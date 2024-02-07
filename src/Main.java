import java.io.*;
import java.nio.ByteBuffer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    public static final int blkSize = 4096;
    public static void main(String[] args) {


        //BlkSizeExp();

        // create mock array:








        // test go from int -> bitarray -> int
        // and then append flag
        // for one example
        // and then decode
        // and then loop








        //printBitArray(encodeTest(array));

        // test that your convert function works

        // and the go slower with the whole thing

        // you always write too much code and then struggle to see if it works.





        // read drive and count non-nulls

        // this is actually the dest
        //String destDev = "/dev/sda1";

        //String sourceDev = "/dev/sda2";

        //countNonNulls(sourceDev);



//
//
//
//
//
//        // create file on disk to write nulls
//        String file = "/media/leeam/vol3/nulls.txt";
//        writeNulls(file);
//        file = "/media/leeam/vol4/nulls.txt";
//        writeNulls(file);


//
//
//        // count non-nulls again to verify thing worked
//        countNonNulls(sourceDev);
//
//
//        // read destination into blocks and then hash
//
//        // <block, index>
//        Hashtable<byte[],Integer> map=new Hashtable<byte[],Integer>();
//
//        readAndHash(map, destDev);
//
//        // send to source
//
//        // source hash search for hashes from dest
//        // search for occurences o
//
//        String results = searchSource(map, sourceDev);


        //read 5 blocks from dest
        String drive = "/media/leeam/vol1/RaspbianInstalls";

        Hashtable<byte[],Integer> map=new Hashtable<byte[],Integer>();
        readAndHash(map, drive, 5);

        // start with whole bytes and then convert to bits



        // src does not need ot know where hash is
        // destination does need to know where hash is

        // for img file -> assoc hash file
        //      hash file: consists of hashes for img file, in block order
        //
        //      consider adjacent blocks when match hashes
        //

        //      src knows each hash that exists on the destination

        //


        // write to a new drive


    }

    private static void printBitArray(BitSet bitArray) {

        for( int i = 0; i < bitArray.length(); ) {
            // print one flag

            System.out.println("flag is " + bitArray.get(i));
            i++;


            int value = 0;
            for (int j = 0; j < 32; j++) {
                value += bitArray.get(i) ? (1<< i) : 0;
                i++;
            }

            System.out.println(value);


            // print one int
        }


    }

    public static int BlkSizeExp () {

        String results = null;




        // step 1. match a block with itself

        // Count matched blocks that aren't at the same location

        // count instances that are already in the hash map

        // read one block add to hash map
        // if already exists increment counter
        // counter is the metric that you  use to judge similarity


        // counter * block size is the a better metric

        // write a lil extension so it's not just on byte level limits


        // read blocks into hash maps

        Hashtable<byte[],Integer> map=new Hashtable<byte[],Integer>();

        String drive = "/media/leeam/vol1/RaspbianInstalls/Hydra_16GB_2018_02_05.img";

        int count = readHashCount(512, map, drive);

        // 4096 -> 3889536 * 4096 = 15931539456 / 16000000000 = 

        // 2048 -> 7779072 * 2048 = 15931539456

        // 1024 -> 15558144 * 1024  = 15931539456

        // 512 ->  31116288 * 512 = 15931539456


        System.out.println(count);



        return count;
    }

    public static byte[] encode1(Hashtable<byte[],Integer> table) {

        // byte 0: literal or index

        // byte 1-32: index

        // byte:

        // read one block

        //byte[] block = readBlock(null);
//
//        if (int index = findMatch(block)) {
//
//
//        }

        // search for match

        // encode that thing.


        return null;


    }

    public static EncodeData1 encodeTest(ArrayList<Integer> dest) {

        // this mocks the source drive
        int[] source = {4, 5, 6, 7, 8};

        EncodeData1 returnData = new EncodeData1(420);

        // create a list of all data, literal yes/no, and their value or index

        for (int e: source) {
            int index;
            EncodeDatum1 myDatum;
            if ( (index = dest.indexOf(e)) != -1 ) {
                // index exists write one bit flag and then

                myDatum = new EncodeDatum1(true, ByteBuffer.allocate(2).putShort((short)index).array());

                returnData.data.add(myDatum);

                returnData.indexCount++;
                // converts int to a byte array

            }

            else {

                myDatum = new EncodeDatum1(false, ByteBuffer.allocate(4).putInt(e).array());

                returnData.data.add(myDatum);

                returnData.literalCount++;

            }
        }


        return returnData;

    }

    public static EncodeData1 EncodeBlockTest(Hashtable<byte[],Integer> dest, byte[][] src) {

        // Hashtable<byte[],Integer> (blk val, index)

        EncodeData1 returnData = new EncodeData1(420);


        // iterate through src
        for (byte[] b : src) {

            Integer index = 0;
            if (( index = dest.get(b)) != null) {

                ByteBuffer buf = ByteBuffer.allocate(4);
                buf.putInt(index);
                returnData.data.add(new EncodeDatum1(true,buf.array()));
            } else {
                returnData.data.add(new EncodeDatum1(false,b));
            }

        }

        return returnData;

    }

//    public static byte[] createOutputArray(ArrayList<EncodeDatum1> data) {
//
//        byte[]
//
//    }

    public static byte[] readBlocks(String drive, byte[][] arrays){

        byte[] block = new byte[blkSize];

        if (drive == null) {
            return null;
        }

        return block;
    }

    public static void decode1() {



    }

    public static void writeNulls (String file) {
        try {

            File myFile = new File(file);
            FileOutputStream myWriter = new FileOutputStream(file, true);


            boolean diskFull = false;
            while (!diskFull) {

                try {
                    myWriter.write( (byte) 0);
                } catch (IOException e) {
                    System.out.println("An error occured maybe the file is full.");
                    e.printStackTrace();
                    diskFull = true;
                }
            }

            myWriter.close();
            myFile.delete();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void countNonNulls (String drive) {

        try {
            File myFile = new File(drive);
            FileInputStream src = new FileInputStream(drive);

            //
            long nonNullBlockCount = 0;

            // keep reading until error
            byte[] buf = new byte[1024];
            int c = 0;
            while (c != -1) {
                c = src.read(buf); // error c == -1
                for (int k = 0; k < buf.length; k++) {
                    if (buf[k] != 0) {
                        nonNullBlockCount++;
                        break;
                    }
                }
            }
            System.out.println(nonNullBlockCount);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readAndHash (Hashtable map, String drive, int nBlocks) {

        // log the locations of the blocks when you send them
        //



        try {
            File myFile = new File(drive);
            FileInputStream src = new FileInputStream(drive);

            int c = 0;
            int i = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1 && i<nBlocks) {
                c = src.read(buf);
                for (int k = 0; k < buf.length; k++) {
                    if (buf[k] != 0) {
                        // put the hash
                        map.put(buf, i); // store block and index
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readAndHash (Hashtable map, String drive) {

        // log the locations of the blocks when you send them
        // 



        try {
            File myFile = new File(drive);
            FileInputStream src = new FileInputStream(drive);

            int c = 0; 
            int i = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1) {
                c = src.read(buf);
                for (int k = 0; k < buf.length; k++) {
                    if (buf[k] != 0) {
                        // put the hash
                        //System.out.println("i am here also ");
                        map.put(buf, i); // store block and index
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String searchSource(Hashtable<byte[], Integer> map, String drive) {


        // hash the source
        // search map
        // write index to string if found, else write literal block val

        String result = "";

        try {
            File myFile = new File(drive);
            FileInputStream src = new FileInputStream(drive);


            int c = 0;
            byte[] buf = new byte[4096];
            while (c != -1) {
                c = src.read(buf);
                Integer index;
                if ( (index = map.get(buf)) != null)   {
                    result = result + index;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return null;
    }


    public static String shell(String command) {


        String s;
        Process p;
        String result = new String();
        try {
            p = Runtime.getRuntime().exec(command);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                result += "line: " + s + "\n";
            p.waitFor();
            result +=  "exit: " + p.exitValue();
            p.destroy();
        } catch (Exception e) {}

        return result;
    }


    // This read, hashes and returns a count when the block has already been seen.
    public static int readHashCount (int blkSize, Hashtable map, String drive) {


        int counter = 0;
        // log the locations of the blocks when you send them
        //
        try {
            File myFile = new File(drive);
            FileInputStream src = new FileInputStream(drive);

            int c = 0;
            int i = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1) {
                c = src.read(buf);
                // if key exists in map
                // inc counter

                if (map.containsKey(buf)) {
                    counter++;
                } else {
                    map.put(buf, i);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }



}