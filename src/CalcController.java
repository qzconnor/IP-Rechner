import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
    Dieses Projekt ist in Zusammenarbeit mit
    Connor Breuer, Claas Diedrich, Lasse Knauff
    entstanden.
 */
public class CalcController implements ActionListener {

    private View view;
    private CalcModel calcModel;
    public CalcController(View view){
        this.view = view;
        this.calcModel = new CalcModel();

    }

    @Override
    public void actionPerformed( ActionEvent e) {

        String rawIP = view.getIP();
        String rawSUB = view.getSubnet();
        if(!validateIP(rawIP)){
            view.getTextArea().setForeground(Color.RED);
            view.getTextArea().setText("Error...\nIP Address is invalid.");
            return;
        }
        if(!validateIP(rawSUB) || !validateSUB(calcModel.ipToBinary(rawSUB.split("\\.")))){
            view.getTextArea().setForeground(Color.RED);
            view.getTextArea().setText("Error...\nSubnet Mask is invalid.");
            return;
        }
        view.getTextArea().setForeground(Color.BLACK);
        ArrayList<Result> results = new ArrayList<>();
        results.add(new Result("Binary", calcModel.ipToBin(rawIP.split("\\."))));
        results.add(new Result("HEX", calcModel.ipToHex(rawIP.split("\\."))));
        results.add(new Result("SHORT SUFFIX",rawIP + "/"+ calcModel.count(calcModel.ipToBinary(rawSUB.split("\\.")), 1)));
        results.add(new Result("NETWORK", calcModel.calcAdresses(rawIP, rawSUB)[0]));
        results.add(new Result("FIRST", calcModel.calcAdresses(rawIP, rawSUB)[2]));
        results.add(new Result("LAST", calcModel.calcAdresses(rawIP, rawSUB)[3]));
        results.add(new Result("BROADCAST", calcModel.calcAdresses(rawIP, rawSUB)[1]));
        results.add(new Result("MAX HOST", String.valueOf(calcModel.calcHosts(rawSUB))));
        results.add(new Result("SPECIAL IP", calcModel.reservedDescription(rawIP) == null ? "NO" : "YES | " + calcModel.reservedDescription(rawIP)));
        view.setResults(results);


    }
    public boolean validateIP(String ip){
        String regex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ip);
        return m.matches();
    }
    public boolean validateSUB(int[] ip){
        boolean valid = true;
        boolean foundZero = false;
        if(ip[ip.length-1] == 255) valid = false;
        for (int i = 0; i < ip.length; i++) {
            if(ip[i] == 0){
                foundZero = true;
            }
            if(foundZero && ip[i] == 1) valid = false;
        }
        return valid;
    }
}
