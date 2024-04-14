import java.lang.reflect.Array;
import java.util.Iterator;

public class HashMapDict<K,V> implements ProjOneDictionary<K,V> {

    Node curr;
    int numberOfActualNodes = 0;
    int numberOfDeletedNodes = 0;
    int totalSize = 10;
    double fullDecimal;


    private class Node {
        K key;
        V value;
        boolean deleted;
        Node(K newKey, V newValue) {
            super();
            key = newKey;
            value = newValue;
            deleted = false;
        }
    }
    private class HashmapIterator implements Iterator<K> {
        Node cursor;
        int pos = 0;
        int firstPos;

        private HashmapIterator() {
            pos = 0;
            if(arrayOfNodes[pos] != null && !arrayOfNodes[pos].deleted) {
                cursor = arrayOfNodes[pos];
            } else {
                while(arrayOfNodes[pos] == null || arrayOfNodes[pos].deleted) {
                    pos+=1;
                    if(pos == totalSize - 1) {
                        break;
                    }
                }
                cursor = arrayOfNodes[pos];
            }
            firstPos = pos;
//            System.out.println("firstPos : " + firstPos);
        }
        @Override
        public boolean hasNext() {

            if(cursor == null) {
                return false;
            } else {
                return true;
            }
            //returns true if cursor node exists, else false
        }//fix 1
        @Override
        public K next() {
//            System.out.println("next() called");
            K toRet;
            if(cursor == null) {
                toRet = null;
            } else {
                toRet = cursor.key;//can be null
            }


//            System.out.println("Pos : " + pos);
            if(pos >= totalSize - 1) {
                pos = 0;
            } else {
                pos+=1;
            }
//            System.out.println("Pos : " + pos);
            if(pos == firstPos) {
                cursor = null;
                return toRet;
            }
            if(arrayOfNodes[pos] != null && !arrayOfNodes[pos].deleted) {
                cursor = arrayOfNodes[pos];
            } else {

                while(arrayOfNodes[pos] == null || arrayOfNodes[pos].deleted) {
                    if(pos >= totalSize - 1) {
                        pos = 0;
                    } else {
                        pos+=1;
                    }
//                    System.out.println("Pos : " + pos);
                    if(pos == firstPos) {
//                        System.out.println("Pos = firstpos : ");
                        if((arrayOfNodes[pos] == null || arrayOfNodes[pos].deleted)) {
//                            System.out.println("looped here");
                            break;
                        } else {
//                            System.out.println("looped here1");
                            cursor = null;
                            return toRet;
                        }

                    }
                }
                cursor = arrayOfNodes[pos];

            }
            return toRet;
        }
    }
    Node[] arrayOfNodes = (Node[])Array.newInstance(Node.class, totalSize);
    //Makes Each element in the array null at the beginning.

    public void resizeArray() {
        numberOfDeletedNodes = 0;
        numberOfActualNodes = 0;
        int originalSize = totalSize;
        totalSize*=2;
        Node[] arrayOfNodes1 = (Node[])Array.newInstance(Node.class, totalSize);
        for(int x = 0; x < originalSize;x++) {
            Node n1 = arrayOfNodes[x];
            if(n1 != null && !n1.deleted) {
                Node newNode = new Node(n1.key,n1.value);
                newNode.deleted = false;
                //all nodes are marked as not deleted when resizing,
                // only the original not deleted ones
                int newPosition = newNode.key.hashCode() % totalSize;
                arrayOfNodes1[newPosition] = newNode;
                numberOfActualNodes+=1;
//                System.out.println("(resizeInsert)New Position got for key " + newNode.key + " is " + newPosition);
            }
        }
        arrayOfNodes = arrayOfNodes1;
    }
    public void printHashmap() {
        System.out.println("Printing Hashmap:");

        for(int x = 0; x < totalSize; x++) {

            if(arrayOfNodes[x] != null) {
                System.out.println("[" + x + "] : " + arrayOfNodes[x].key + "," + arrayOfNodes[x].value + " deleted:" + arrayOfNodes[x].deleted);
            } else {
                System.out.println("[" + x + "] : null");
            }
        }
        System.out.println("Total Size / Capacity:" + totalSize);
        System.out.println("Filled Buckets:" + numberOfActualNodes);
        System.out.println("Deleted Buckets:" + numberOfDeletedNodes);
    }


    @Override
    public boolean insert(K key, V value) throws NullValueException {
        if (value == null) {throw new NullValueException();}
        Node newNode = new Node(key,value);
        int position = key.hashCode() % totalSize;
//        System.out.println("(Insert)Position got for key " + key + " is " + position);
        int original = position;
        if(arrayOfNodes[original] == null) {
            arrayOfNodes[position] = newNode;
            numberOfActualNodes+=1;
            float division = ((float)(numberOfActualNodes) / ((float)totalSize));
            if(division >= 0.5) {
                //double the array size
//                System.out.println("Resized array for index " + position + "," + value);
                resizeArray();
            }
            return false;
        }
        if(arrayOfNodes[position].key == key && !arrayOfNodes[position].deleted) {
            arrayOfNodes[position].value = value;
            return true;//because key is already in list, return true
        } else {
            if(position == totalSize - 1) {
                position = 0;
            } else {
                position+=1;
            }
            while(position != original) {
                if(arrayOfNodes[position] == null) {//should stop searching once a null node is found
                    break;
                }
                if(arrayOfNodes[position].key == key && !arrayOfNodes[position].deleted) {
                    arrayOfNodes[position].value = value;
                    return true;
                }
                if(position == totalSize - 1) {
                    position = 0;
                } else {
                    position+=1;
                }
            }
            //went through and didn't find key,
            //Make new node

            position = original;
            if(arrayOfNodes[position] == null) {
                arrayOfNodes[position] = newNode;
                numberOfActualNodes+=1;
                float division = ((float)(numberOfActualNodes) / ((float)totalSize));
                if(division >= 0.5) {
                    //double the array size
                    resizeArray();
                }
                return false;
            } else {
                if(position == totalSize - 1) {
                    position = 0;
                } else {
                    position+=1;
                }
                while(position != original) {//look for next null position to fill
                    if(arrayOfNodes[position] == null) {
                        arrayOfNodes[position] = newNode;
                        numberOfActualNodes+=1;
                        float division = ((float)(numberOfActualNodes) / ((float)totalSize));
                        if(division >= 0.5) {
                            //double the array size
                            resizeArray();
                        }
                        return false;
                    }
                    if(position == totalSize - 1) {
                        position = 0;
                    } else {
                        position+=1;
                    }
                }
                System.out.println("Should never print, unless error with insert()");
                return false;
            }
        }



    }

    @Override
    public V find(K key) {
        //implement find first to use in insert
        int position = key.hashCode() % totalSize;

        int original = position;
        if(arrayOfNodes[original] == null) {//added
            return null;
        }
        if(arrayOfNodes[position].key == key && !arrayOfNodes[position].deleted) {
            return arrayOfNodes[position].value;
        } else {
            //linear probing, look at next ones
            if(position == totalSize - 1) {
                position = 0;
            } else {
                position+=1;
            }
            while(position != original) {
                if(arrayOfNodes[position] == null) {//added
                    break;
                }
                if(arrayOfNodes[position].key == key && !arrayOfNodes[position].deleted) {
                    return arrayOfNodes[position].value;
                }

                if(position == totalSize - 1) {
                    position = 0;
                } else {
                    position+=1;
                }
            }
            return null;

        }

    }

    @Override
    public boolean delete(K key) {
        int position = key.hashCode() % totalSize;
        int original = position;
//        System.out.println("(Delete)Position got for key " + key + " is " + position);
        if(arrayOfNodes[position] == null) {
            return false;//key is not found
        }

        if(arrayOfNodes[position].key == key && !arrayOfNodes[position].deleted) {
            arrayOfNodes[position].deleted = true;
//            System.out.println("Test1 + delNodes before = " + numberOfDeletedNodes);
            numberOfDeletedNodes+=1;
//            System.out.println("Test1 + delNodes = " + numberOfDeletedNodes);
            return true;
        } else {
            if(position == totalSize - 1) {
                position = 0;
            } else {
                position+=1;
            }
            while(position != original) {

                if(arrayOfNodes[position] == null) {//added
                    break;
                }
                if(arrayOfNodes[position].key == key && !arrayOfNodes[position].deleted) {
                    arrayOfNodes[position].deleted = true;
                    numberOfDeletedNodes+=1;
                    return true;
                }

                if(position == totalSize - 1) {
                    position = 0;
                } else {
                    position+=1;
                }
            }
            return false;

        }

    }

    @Override
    public int getSize() {
        return numberOfActualNodes - numberOfDeletedNodes;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashmapIterator();
    }
}
