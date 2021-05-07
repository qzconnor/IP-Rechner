import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
    Dieses Projekt ist in Zusammenarbeit mit
    Connor Breuer, Claas Diedrich, Lasse Knauff
    entstanden.
 */
public class View {
    private JFrame jFrame = new JFrame();


    private JTextField ipInput;
    private JTextField subnetInput;
    private JButton calcbutton;
    private JTextArea textArea;

    public View(){
        init();
    }

    private void init(){
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setSize(450, 310);
        jFrame.setTitle("IP-Rechner");
        jFrame.setLocationRelativeTo(null);

        Container cp = jFrame.getContentPane();
        cp.setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        JPanel data = new JPanel(new BorderLayout());

        JPanel ipInputPanel = new JPanel(new BorderLayout());
        JPanel subnetPanel = new JPanel(new BorderLayout());


        ipInput = new JTextField();
        subnetInput = new JTextField();
        calcbutton = new JButton("Calculate");

        calcbutton.addActionListener(new CalcController(this));


        ipInput.setSize(jFrame.getWidth(), 20);
        subnetInput.setSize(jFrame.getWidth(), 20);
        calcbutton.setSize(jFrame.getWidth(), 20);


        ipInputPanel.add(new JLabel("IP Adress:"), BorderLayout.NORTH);
        ipInputPanel.add(ipInput, BorderLayout.CENTER);
        top.add(ipInputPanel, BorderLayout.NORTH);
        subnetPanel.add(new JLabel("Subnet Mask:"), BorderLayout.NORTH);
        subnetPanel.add(subnetInput, BorderLayout.CENTER);
        top.add(subnetPanel, BorderLayout.CENTER);
        top.add(calcbutton, BorderLayout.SOUTH);



        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(jFrame.getWidth(), 197));
        textArea.setEditable(false);



        data.add(textArea, BorderLayout.NORTH);

        cp.add(top, BorderLayout.NORTH);
        cp.add(data, BorderLayout.CENTER);

        jFrame.setVisible(true);

    }

    public String getIP(){
        return ipInput.getText();
    }
    public String getSubnet(){
        return subnetInput.getText();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setResults(ArrayList<Result> results){
        textArea.setText("");
        StringBuilder builder = new StringBuilder();
        for(Result result : results){
            builder.append(result.getName() + ": " + result.getData() + "\n");
        }
        textArea.setText(builder.toString());
    }
}
