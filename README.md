# Block Compressor

This program rectifies two hard drives making one a byte level copy of the other. The program keeps a record of available blocks on the destination so they can omit these blocks from the stream of data, it then compresses the stream of data. On the destination it then recreates the source disk using the blocks available and blocks transmitted. Since it is a byte level copy it is file-system agnostic. 
