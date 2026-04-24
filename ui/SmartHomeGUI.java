package ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.*;
import logic.*;

public class SmartHomeGUI extends JFrame{
    private ArrayList<User> users=new ArrayList<>();
    private ArrayList<Device> devices=new ArrayList<>();
    private User currentUser;
    
    private int nextDeviceId=101;
    private int nextUserId=3;

    private JPanel mainPanel,roomContainer;
    private JTextArea aiLogArea;
    private CardLayout cardLayout;

    public SmartHomeGUI(){
        loadData();
        if (users.isEmpty()){
            users.add(new Admin(1, "Berke", "1234", "Full_Access"));
            users.add(new User(2, "User1", "pass"));
        }
        if (devices.isEmpty()){
            devices.add(new Light(nextDeviceId++, "Lamp", "Living Room"));
            devices.add(new Light(nextDeviceId++, "Lamp", "Kitchen"));
            devices.add(new Thermostat(nextDeviceId++, "AC", "Bedroom"));
        }

        setTitle("AI Smart Home OS - Professional Edition");
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout=new CardLayout();
        mainPanel=new JPanel(cardLayout);
        mainPanel.add(createLoginPanel(),"Login");
        add(mainPanel);

        this.addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent e){ saveData(); }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void logAction(String action){
        String timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logEntry="[" + timestamp + "] User: " + currentUser.getName() + " -> " + action;
        
        try (PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("system_logs.txt", true)))) {
            out.println(logEntry);
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
        aiLogArea.append("[SYSTEM LOG] " + action + "\n");
    }

    private JPanel createLoginPanel(){
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(34, 49, 63));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets=new Insets(10, 10, 10, 10);

    JLabel title=new JLabel("SMART HOME LOGIN");
    title.setForeground(Color.WHITE);
    title.setFont(new Font("Segoe UI", Font.BOLD, 30));
    gbc.gridwidth=2;gbc.gridy=0; 
    panel.add(title, gbc);

    JLabel userLabel=new JLabel("Username:");
    userLabel.setForeground(Color.WHITE);
    gbc.gridwidth=1; gbc.gridy=1; gbc.gridx=0; 
    panel.add(userLabel, gbc);

    JTextField u=new JTextField("Berke", 15);
    gbc.gridx=1; 
    panel.add(u, gbc);

    JLabel passLabel=new JLabel("Password:");
    passLabel.setForeground(Color.WHITE);
    gbc.gridy=2;gbc.gridx=0; 
    panel.add(passLabel, gbc);

    JPasswordField p=new JPasswordField("1234", 15);
    gbc.gridx=1; 
    panel.add(p, gbc);

    JButton login=new JButton("Login");
    login.setBackground(new Color(41, 128, 185));
    login.setForeground(Color.WHITE);
    gbc.gridy=3; gbc.gridx=0;gbc.gridwidth = 2; 
    panel.add(login, gbc);

    login.addActionListener(e -> {
        for (User user : users){
            if (user.getName().equals(u.getText()) && user.login(u.getText(), new String(p.getPassword()))) {
                currentUser=user;
                mainPanel.add(createDashboard(), "Dash");
                cardLayout.show(mainPanel, "Dash");
                logAction("User Logged In");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Access Denied!");
    });

    return panel;
}

    private JPanel createDashboard(){
        JPanel dash=new JPanel(new BorderLayout());
        
        JPanel topBar=new JPanel(new BorderLayout());
        topBar.setBackground(new Color(41, 128, 185));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JLabel welcome = new JLabel("Connected as: " + currentUser.getName() + " (" + (currentUser instanceof Admin ? "Administrator" : "Standard User") + ")");
        welcome.setForeground(Color.WHITE);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            logAction("User Logged Out");
            cardLayout.show(mainPanel, "Login");
        });
        topBar.add(welcome, BorderLayout.WEST);
        topBar.add(logoutBtn, BorderLayout.EAST);
        dash.add(topBar, BorderLayout.NORTH);

        roomContainer=new JPanel(new GridLayout(0, 2, 15, 15));
        updateDeviceView();
        dash.add(new JScrollPane(roomContainer), BorderLayout.CENTER);

        JPanel bottomPanel=new JPanel(new BorderLayout());
        JPanel voiceBar=new JPanel(new FlowLayout(FlowLayout.LEFT));
        voiceBar.setBackground(new Color(52, 73, 94));
        JLabel micLabel=new JLabel("[ MIC ]"); 
        micLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        micLabel.setForeground(Color.ORANGE);
        JTextField cmdField=new JTextField(45);
        JButton sendBtn=new JButton("EXECUTE");
        sendBtn.addActionListener(e -> { processVoiceCommand(cmdField.getText()); cmdField.setText(""); });
        voiceBar.add(micLabel); voiceBar.add(new JLabel(" Voice Command: "));
        voiceBar.add(cmdField); voiceBar.add(sendBtn);

        aiLogArea=new JTextArea(8, 50);
        aiLogArea.setEditable(false);
        aiLogArea.setBackground(Color.BLACK);
        aiLogArea.setForeground(new Color(0, 255, 65));
        bottomPanel.add(voiceBar, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(aiLogArea), BorderLayout.CENTER);
        dash.add(bottomPanel, BorderLayout.SOUTH);

        if (currentUser instanceof Admin){
            dash.add(createAdminSidebar((Admin)currentUser), BorderLayout.EAST);
        }
        return dash;
    }

    private void processVoiceCommand(String cmd){
        String input=cmd.toLowerCase();
        aiLogArea.append("> NLP Processing: \"" + cmd + "\"\n");
        if (new AIAssistant(1, "Jarvis").authenticateVoice(currentUser)) {
            boolean found=false;
            int extractedValue=-1;
            Pattern p=Pattern.compile("\\d+");
            Matcher m=p.matcher(input);
            if (m.find()) extractedValue=Integer.parseInt(m.group());

            for (Device d : devices){
                if (input.contains(d.getRoomName().toLowerCase()) && input.contains(d.getName().toLowerCase())) {
                    if (extractedValue != -1) {
                        if (d instanceof Light) {
                            ((Light) d).adjustBrightness(Math.min(100, Math.max(0, extractedValue)));
                            logAction("AI adjusted " + d.getRoomName() + " " + d.getName() + " to " + extractedValue + "%");
                        } else if (d instanceof Thermostat) {
                            ((Thermostat) d).setTemperature(Math.min(32, Math.max(15, extractedValue)));
                            logAction("AI adjusted " + d.getRoomName() + " " + d.getName() + " to " + extractedValue + "°C");
                        }
                        found=true;
                    } 
                    if (input.contains("on") || input.contains("open")){
                        d.turnOn();
                        logAction("AI turned ON " + d.getRoomName() + " " + d.getName());
                        found=true;
                    } else if (input.contains("off") || input.contains("close")) {
                        d.turnOff();
                        logAction("AI turned OFF " + d.getRoomName() + " " + d.getName());
                        found=true;
                    }
                }
            }
            if (!found) aiLogArea.append("[AI ERROR] Device or room not recognized.\n");
            updateDeviceView();
        }
    }

    private JPanel createAdminSidebar(Admin adm) {
        JPanel side=new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setPreferredSize(new Dimension(300, 0));
        side.setBorder(BorderFactory.createTitledBorder("System Management"));
        JPanel f=new JPanel(new GridBagLayout());
        GridBagConstraints g=new GridBagConstraints();
        g.fill=GridBagConstraints.HORIZONTAL; g.insets = new Insets(5,5,5,5);

        g.gridx=0; g.gridy=0; g.gridwidth=2; f.add(new JLabel("--- DEVICE REGISTRATION ---"), g);
        JTextField dIdField=new JTextField("Next ID: " + nextDeviceId); dIdField.setEditable(false);
        JTextField dName=new JTextField(10);
        JComboBox<String> rBox=new JComboBox<>(new String[]{"Living Room", "Kitchen", "Bedroom", "Bathroom", "Entrance"});
        JComboBox<String> tBox=new JComboBox<>(new String[]{"Light", "Thermostat", "Door"});
        g.gridwidth=1; g.gridy=1; f.add(new JLabel("ID:"), g); g.gridx=1; f.add(dIdField, g);
        g.gridx=0; g.gridy=2; f.add(new JLabel("Name:"), g); g.gridx=1; f.add(dName, g);
        g.gridx=0; g.gridy=3; f.add(new JLabel("Room:"), g); g.gridx=1; f.add(rBox, g);
        g.gridx=0; g.gridy=4; f.add(new JLabel("Type:"), g); g.gridx=1; f.add(tBox, g);
        JButton addD = new JButton("Add Device");
        g.gridx=0; g.gridy=5; g.gridwidth=2; f.add(addD, g);

        g.gridy=6; f.add(new JLabel(" "), g); 

        g.gridy=7; f.add(new JLabel("--- USER REGISTRATION ---"), g);
        JTextField uIdField = new JTextField("Next ID: " + nextUserId); uIdField.setEditable(false);
        JTextField uN = new JTextField(10); JTextField uP = new JTextField(10);
        g.gridwidth=1; g.gridy=8; f.add(new JLabel("ID:"), g); g.gridx=1; f.add(uIdField, g);
        g.gridx=0; g.gridy=9; f.add(new JLabel("Name:"), g); g.gridx=1; f.add(uN, g);
        g.gridx=0; g.gridy=10; f.add(new JLabel("Pass:"), g); g.gridx=1; f.add(uP, g);
        JButton addU = new JButton("Add User");
        g.gridx=0; g.gridy=11; g.gridwidth=2; f.add(addU, g);

        g.gridy=12; f.add(new JLabel(" "), g);
        JButton remU = new JButton("Remove User");
        remU.setBackground(new Color(192, 57, 43)); remU.setForeground(Color.WHITE);
        g.gridy=13; f.add(remU, g);

        g.gridy=14; f.add(new JLabel(" "), g); 
        JButton aiBtn = new JButton("AI Settings");
        g.gridy=15; f.add(aiBtn, g);

        addD.addActionListener(e -> {
            Device d; String r=(String)rBox.getSelectedItem();
            if(tBox.getSelectedItem().equals("Light")) d = new Light(nextDeviceId++, dName.getText(), r);
            else if(tBox.getSelectedItem().equals("Thermostat")) d = new Thermostat(nextDeviceId++, dName.getText(), r);
            else d=new Door(nextDeviceId++, dName.getText(), r);
            devices.add(d); 
            dIdField.setText("Next ID: " + nextDeviceId);
            logAction("Added Device: " + dName.getText() + " in " + r);
            updateDeviceView();
        });

        addU.addActionListener(e -> {
            User nu=new User(nextUserId++, uN.getText(), uP.getText());
            nu.registerVoice(); users.add(nu); 
            uIdField.setText("Next ID: " + nextUserId);
            logAction("Added User: " + uN.getText());
            JOptionPane.showMessageDialog(this, "New user registered.");
        });

        remU.addActionListener(e -> {
            ArrayList<String> names=new ArrayList<>();
            for(User u : users) if(!(u instanceof Admin)) names.add(u.getName());
            String s = (String)JOptionPane.showInputDialog(this, "Select User:", "Remove User", 0, null, names.toArray(), null);
            if(s != null) {
                users.removeIf(u -> u.getName().equals(s));
                logAction("Removed User: " + s);
            }
        });

        aiBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "AI Engine Status: Active\nModel: Jarvis NLP\nVersion: 3.1.2");
            adm.manageAI();
        });

        side.add(f);
        return side;
    }

    private void updateDeviceView() {
        roomContainer.removeAll();
        String[] rooms={"Living Room", "Kitchen", "Bedroom", "Bathroom", "Entrance"};
        for (String r : rooms) {
            JPanel p = new JPanel(); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBorder(BorderFactory.createTitledBorder(r));
            for (Device d : devices) {
                if (d.getRoomName().equals(r)) {
                    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JButton b = new JButton(d.getStatus() ? "ON" : "OFF");
                    b.setBackground(d.getStatus() ? Color.GREEN : Color.LIGHT_GRAY);
                    b.addActionListener(x -> { 
                        if(d.getStatus()) { d.turnOff(); logAction("Manual: Turned OFF " + d.getName()); }
                        else { d.turnOn(); logAction("Manual: Turned ON " + d.getName()); }
                        updateDeviceView(); 
                    });
                    row.add(new JLabel(d.getName())); row.add(b);
                    if(d instanceof Light) {
                        Light l = (Light)d; JSlider s = new JSlider(0, 100, l.getBrightness());
                        s.addChangeListener(z -> { l.adjustBrightness(s.getValue()); if(!s.getValueIsAdjusting()) logAction("Manual: Adjusted " + d.getName() + " brightness to " + s.getValue() + "%"); });
                        row.add(s); row.add(new JLabel(l.getBrightness() + "%"));
                    }
                    if(d instanceof Thermostat) {
                        Thermostat t = (Thermostat)d; JSlider s = new JSlider(15, 32, t.getTemperature());
                        s.addChangeListener(z -> { t.setTemperature(s.getValue()); if(!s.getValueIsAdjusting()) logAction("Manual: Adjusted " + d.getName() + " temp to " + s.getValue() + "°C"); });
                        row.add(s); row.add(new JLabel(t.getTemperature() + "°C"));
                    }
                    p.add(row);
                }
            }
            roomContainer.add(p);
        }
        roomContainer.revalidate(); roomContainer.repaint();
    }

    private void saveData() {
        try (ObjectOutputStream u = new ObjectOutputStream(new FileOutputStream("users.dat"));
             ObjectOutputStream d = new ObjectOutputStream(new FileOutputStream("devices.dat"))) {
            u.writeObject(users); d.writeObject(devices);
        } catch (Exception e) {}
    }

    private void loadData() {
        try (ObjectInputStream u = new ObjectInputStream(new FileInputStream("users.dat"));
             ObjectInputStream d = new ObjectInputStream(new FileInputStream("devices.dat"))) {
            users = (ArrayList<User>) u.readObject();
            devices = (ArrayList<Device>) d.readObject();
            if(!users.isEmpty()) nextUserId = users.get(users.size()-1).getUserID() + 1;
            if(!devices.isEmpty()) nextDeviceId = devices.get(devices.size()-1).getId() + 1;
        } catch (Exception e) {}
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(SmartHomeGUI::new); }
}