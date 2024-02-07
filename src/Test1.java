import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.SpringLayout;

public class Test1 {


    static int blkSize = 4096; 

    static int hashSize = 32;


    /**Things to ASk rex 
    * How to store Hashebs on Destination Side
    
    Currently not Storing, Reading img into HashMap<ByteBuffer, FileLocation{Name, OffSet}>

    * ask about byteBuffer, vs byte[]
    * using ByteBuffer in set and Map

    * use it inset because ByteBuffer.equals() works 
    * whilst byte[].equals doesn't work 


    * Program currently: 
    * img -> HashDump (sent to source)
    * Encode Src with HashDump

    * Destination must store blockSize as well 
        * can use XML file

    


    * img -> HashMap (Hash, FileLocation)



    TODO Decode:

    *
    */





    // byte[] set;







    // Test your new class:


    // And then do some more stuff





    // img -> hashmap 

    
    // img -> hash file
    // can store hashes as raw bytes on a file. 



    // src hashmap, dest hashmap -> byte[]

    // byte[] -> disk img 

    public static void main(String[] args) {

        /**
        String input = "/media/leeam/vol1/RaspbianInstalls/2020-02-13-raspbian-buster.img"; 
        //String output = "/media/leeam/vol1/RaspbianInstalls/Hydra_output.img";

        String hashFile = "/media/leeam/vol1/RaspbianInstalls/Hashes/2020-02-13-raspbian-buster.hash";

        //testing(input, hashFile);

        // test2();


        //imgtoHashFile(input, hashFile, "SHA-256");
        
        HashSet<ByteBuffer> myHashes = new HashSet<ByteBuffer>();
        readHashFile(hashFile, myHashes);


        //EncodeData1 encodedDataWrapper = new EncodeData1();
        //encodeImg(encodedDataWrapper, input, myHashes);


        String outputFile = "/media/leeam/vol1/EncodedOutputs/Encode1.bin";
        File outFile = new File(outputFile);

        outFile.getParentFile().mkdirs();

        encodedWholeImage(input, outputFile, myHashes);

        myHashes = null;

        String decodeString = "/media/leeam/vol1/DecodedOutputs/Decode1.img";
        File decodeFile = new File(decodeString);

        decodeFile.getParentFile().mkdirs();


        HashMap<ByteBuffer, FileLocation> map = new HashMap<ByteBuffer, FileLocation>();

        imgToHashMap(map, input);

        decodeDump(outputFile, decodeString, map);






        //writeEncodeDataToFile(encodedDataWrapper, outputFile);

         **/


        byteArray testArray = new byteArray( new byte[]{0,1,2,3,4});

        byteArray testArray2 = new byteArray( new byte[] {0,1,2,3,4});

        byteArray testArray3 = new byteArray( new byte[] {0,1,2});


        HashSet<byteArray> testSet = new HashSet<byteArray>();

        testSet.add(testArray);

        System.out.println(testSet.size());

        testSet.add(testArray2);

        System.out.println(testSet.size());

        testSet.add(testArray3);

        System.out.println(testSet.size());












        // imgtohashFile 

        // readHashFile 

        // readFile and then search hashes for matches. (should be 100% match)


        // TODO NOW: 

        // read all HashFiles into a set. 

        // read One block and then search the hash and then encode immediately.







        // returns true if the block exists in the hashFiles, can take one or many Hashfiles


        // encodeImg() 

        // EncodeData / Encode Datum, create encoder to write that to file. 

        // decodeImg()
    
        

    }

    // HashMap<blockHash, IndexOnFile>
    // IndexOnFile : FileName, Offset
    public static void writeHashMapToFile() {

    }

    public static void imgToHashMap(HashMap<ByteBuffer, FileLocation> map, String file) {

        try {
            File myFile = new File(file);
            FileInputStream myStream = new FileInputStream(myFile);

            byte[] buf = new byte[blkSize];
            int i = 0;
            int c = myStream.read(buf);
            while(c!= -1){

                map.put(ByteBuffer.wrap(buf) , new FileLocation(file,i));
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    
    }

    public static void readHashFileToMap(String hashMapFile){}

    public static void decodeDump(String inFile, String outFile, HashMap<ByteBuffer, FileLocation> map) {


        try{


            System.out.println(inFile);

            System.out.println(outFile);


            FileInputStream inputFile = new FileInputStream(new File(inFile));
            FileOutputStream outputFile = new FileOutputStream(new File(outFile));

            byte[] buf = new byte[1];
            byte[] hash = new byte[32];

            int c = inputFile.read(buf);

            


            while(c != -1) {


                if (buf[0] == (byte) 1 ) {

                    // literal value read

                    // write next blockSize to File


                    outputFile.write(inputFile.readNBytes(blkSize));

                } else {

                    // Find Block on Hash from dataset 

                    inputFile.read(hash);

                    ByteBuffer bb = ByteBuffer.wrap(hash);

                    FileLocation location = map.get(bb);

                    FileInputStream readImg = new FileInputStream(location.fileName);

                    readImg.skip(location.offSet * blkSize);

                    outputFile.write( readImg.readNBytes(blkSize) );

                    readImg.close();

                }


                c = inputFile.read(buf);


            }

            inputFile.close();
            outputFile.close();



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{}
        



    }

    public static void encodedWholeImage(String inFile, String outFile, HashSet<ByteBuffer> set) {

        try {
            FileInputStream src = new FileInputStream(inFile);
            FileOutputStream dest = new FileOutputStream(outFile);
            
            int c = 0;
            byte[] buf = new byte[blkSize];
            
            while (c != -1) {
                // returns the total number of bytes read into the buffer. 
                c = src.read(buf);
                
                encodeBlockToFile(dest, buf, set);
                
            }
            src.close();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public static void encodeBlockToFile(FileOutputStream outputFile, byte[] block, HashSet<ByteBuffer> set) {


        

        try {
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(block);
            if (set.contains(ByteBuffer.wrap(hash))) {
                outputFile.write( (byte) 1); // write one byte as literal flag   
                outputFile.write( hash );                 
            }

            else {
                outputFile.write( (byte) 0);
                outputFile.write(block);
            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // write one byte to file
        catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        


    }

    private static boolean HashExistsInSet(ByteBuffer hash, HashSet<ByteBuffer> set) {
        return false;
    }

//    public static EncodeData1 readEncodeDataFromFile(String file) {
//
//        EncodeData1 data = new EncodeData1(blkSize);
//
//        try {
//
//            File newFile = new File(file);
//            newFile.getParentFile().mkdirs();
//
//            FileInputStream newStream = new FileInputStream(newFile);
//
//            ObjectInputStream ooStream = new ObjectInputStream(newStream);
//
//            data = (EncodeData1) ooStream.readObject(data.getClass());
//
//
//            ooStream.close();
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally{}
//
//    }


    public static void writeEncodeDataToFile(EncodeData1 data, String file) {


        try {
        
            File newFile = new File(file);
            newFile.getParentFile().mkdirs();

            FileOutputStream newStream = new FileOutputStream(newFile);

            ObjectOutputStream ooStream = new ObjectOutputStream(newStream);

            ooStream.writeObject(data);
             

            ooStream.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{}
    
    }

    public static void encodeImg(EncodeData1 data, String file, HashSet<ByteBuffer> set) {

        try {
            FileInputStream src = new FileInputStream(file);
            
            int c = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1) {
                // returns the total number of bytes read into the buffer. 
                c = src.read(buf);
                if (blockExistsInSet(buf, set)) {
                    
                    data.data.add(new EncodeDatum1(true, new byte[0]));
                } else {

                    data.data.add(new EncodeDatum1(false, buf));

                }
            }
            src.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readHashFiles(String[] hashFiles, HashSet<ByteBuffer> set){

        for (String file : hashFiles) {
            readHashFile(file, set);
        }
    }
    
    public static boolean blockExistsInSet(byte[] block, HashSet<ByteBuffer> set) {

        boolean result = false; 

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            ByteBuffer hash = ByteBuffer.wrap(digest.digest(block));

            result = set.contains(hash);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public static void test2() {
        try {
            FileOutputStream test1 = new FileOutputStream("test2.bin");

            byte[] bytes = new byte[] {2};

            System.out.println(bytes);


            test1.write(bytes);

            test1.close();


            byte[] newBytes = new byte[1];
            
            FileInputStream testIn = new FileInputStream("test2.bin");

            testIn.read(newBytes);

            testIn.close();

            System.out.println(newBytes);

            System.out.println(bytes[0] == newBytes[0]);



        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    // read one block and hash/
    // write and then read again and see if it is the same; 
    public static void testing(String input, String output) {


        try {


            // readOne block and hash 


            FileInputStream src = new FileInputStream(input);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] buf = new byte[blkSize];
            System.out.println(src.read(buf));
            byte[] hash = digest.digest(buf);


            System.out.println("hash og == "+ hash);
            src.close();


            // write one hashed block to file;

            File newFile = new File(output);
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
            FileOutputStream dest = new FileOutputStream(newFile);

            System.out.println("hash length ==" + hash.length);
            dest.write(hash);

            dest.close();

            
            // read one hashed block

            FileInputStream hashFile = new FileInputStream(output);

            byte[] readHash = new byte[hashSize];

            System.out.println("chars read == " + hashFile.read(readHash));

            System.out.println(readHash);

            System.out.println(hash);

            System.out.println("a = b?" + byteEquals(readHash, hash));

            hashFile.close();

            src.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        



    }

    // reads one block, hashes and searches for a match 
    // returns number of unmatched blocks.
    public static int searchMatches(HashSet<ByteBuffer> set, String input){

        int unmatchedBlocks = 0; 

        try {
            FileInputStream src = new FileInputStream(input);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            int c = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1) {
                c = src.read(buf);
                byte[] hash = digest.digest(buf);

                if (!set.contains(ByteBuffer.wrap(hash))) unmatchedBlocks++;
            }
            src.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

        return unmatchedBlocks;

    }

    public static void imgtoHashFile(String input, String output, String algorithm) {
        try {
            FileInputStream src = new FileInputStream(input);
            
            File newFile = new File(output);
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
            FileOutputStream dest = new FileOutputStream(newFile);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            int c = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1) {

                // returns the total number of bytes read into the buffer. 
                c = src.read(buf);
                byte[] hash = digest.digest(buf);
                dest.write(hash);
            }
            src.close();
            dest.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // hash.bin -> hashMap
    public static void readHashFile(String input, HashSet<ByteBuffer> set) {

        try {
            FileInputStream src = new FileInputStream(input);

            int c = 0;
            while (c != -1) {
                byte[] buf = new byte[hashSize];
                c = src.read(buf);
                set.add(ByteBuffer.wrap(buf));
            }
            src.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // check 1 to 1
    // readHashFile, search for matching blocks in OG image. 
    public static void copyImg(String input, String output) {

        try {
            FileInputStream src = new FileInputStream(input);
            FileOutputStream dest = new FileOutputStream(output);


            int c = 0;
            byte[] buf = new byte[blkSize];
            while (c != -1) {
                c = src.read(buf);
                dest.write(buf);
            }
            src.close();
            dest.close();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public static Boolean byteEquals(byte[] a, byte[] b) {



        if (a.length != b.length) return false;

        for (int i = 0; i < a.length; i++) {
            
            if (a[i] != b[i]) {
                return false; 
            }
        }

        return true;
    }
    
}

