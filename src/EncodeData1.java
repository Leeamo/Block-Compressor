import java.util.ArrayList;

import java.nio.ByteBuffer;
public class EncodeData1 {

    public ArrayList<EncodeDatum1> data;

    public int literalCount;

    public int indexCount;

    public int blockSize; 

    public EncodeData1(int blockSize) {
        data = new ArrayList<EncodeDatum1>();

        literalCount = 0;

        indexCount = 0;

        this.blockSize =  blockSize;
    }

    public byte[] toBytes(){


        int nBytes = literalCount * blockSize // blocks for each of the literal values
                    + literalCount // blocks to store each of the literal flags for literal
                    + indexCount;

        byte[] returnBytes = new byte[nBytes];

        // loop through

        int i = 0;
        for (EncodeDatum1 ed : data) {

            int flag = ed.literal ? 1: 0;

            returnBytes[i] = (byte) flag;

            i++;

            if (flag == 0) {
                i = writeBlock(returnBytes, i, ed.value);
            }
            // write some stuff to the byte array.
        }


        return returnBytes;
    }

    private int writeBlock(byte[] returnBytes, int index, byte[] value) {
        int j = 0;
        for (j = 0 ; j < blockSize; j++) {

            returnBytes[index + j] =  value[j];
        }

        return index + j;
    }

    @Override
    public String toString() {
        String newString = "";

        short num =  0b010101;

        byte[] byteArray = ByteBuffer.allocate(2).putShort((short)num).array();

        //System.out.println(byteArrtoInt(byteArray));

        for (EncodeDatum1 datum : data) {

            newString = newString + "Literal == ";

            newString = newString + (datum.literal ?  "1" : "0");

            newString = newString + "Value == ";

            //newString = newString + byteArrtoInt(datum.value);

            if (datum.value.length == 2) {
                newString = newString + shortFromByte(datum.value);
            }

            else {
                newString = newString + intFromByte(datum.value);
            }






        }


        return newString;


    }

    //https://stackoverflow.com/questions/7619058/convert-a-byte-array-to-integer-in-java-and-vice-versa

    int intFromByte(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }

    short shortFromByte(byte[] bytes) {
        return (short)(bytes[0] << 8 | (bytes[1] & 0xFF));
    }


}
