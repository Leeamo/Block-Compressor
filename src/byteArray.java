import java.util.Arrays;

public class byteArray {


    public byte[] array;

    public byteArray(byte [] array) {
        this.array = array;
    }


    // overwrite hashcode 


    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof byteArray))
        {
            return false;
        }
        return Arrays.equals(array, ((byteArray)other).array);
    }


    // overwrite equals
    @Override
    public int hashCode()
    {
        return Arrays.hashCode(array);
    }


    // this class wraps a byte Array


    // equals 


    // https://stackoverflow.com/questions/6187294/java-set-collection-override-equals-method
    
}
