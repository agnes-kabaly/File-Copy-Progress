import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteCopy extends SwingWorker<Void, Void>{

    private File fileFrom;
    private File fileTo;

    public ByteCopy(File fileFrom, File fileTo) {
        this.fileFrom = fileFrom;
        this.fileTo = fileTo;
    }

    @Override
    protected Void doInBackground() throws Exception {

        try {
            if(!fileTo.exists()) {
                fileTo.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create file");
        }

        FileInputStream fileInputStream = new FileInputStream(fileFrom);
        long fromFileLength = fileFrom.length();

        FileOutputStream fileOutputStream = new FileOutputStream(fileTo);

        byte[] bytes = new byte[1024];
        long allByteCounter = 0;
        int actualBytes;
        int percent = 0;

        setProgress(0);

        while ((actualBytes = fileInputStream.read(bytes)) != -1){
            if(!this.isCancelled()) {
                allByteCounter += actualBytes;

                percent = (int) Math.floor(((allByteCounter * 1.0) / fromFileLength) * 100);
                setProgress(percent);
                fileOutputStream.write(bytes, 0, actualBytes);
                //System.out.println(percent);
            } else {
                break;
            }
        }

        fileInputStream.close();
        fileOutputStream.close();

        return null;
    }

    @Override
    protected void done() {
        super.done();

        System.out.println("it is ready!");
    }
}

