import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

class LimpaTela {

    public void limparTela() {

        try {
            String command = "sh clear.sh";
            Process proc = Runtime.getRuntime().exec(command);
            InputStream input = proc.getInputStream();
            BufferedInputStream reader = new BufferedInputStream(input);
            Scanner sc = new Scanner(reader);
            while (sc.hasNext()){
                sc.next();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}