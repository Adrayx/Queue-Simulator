package view;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

public class QueueView extends JFrame {
    private final JButton m_test =  new JButton("Begin testing");
    private final JButton m_open = new JButton("Open log file!");
    private final JTextField m_numberClients = new JTextField(5);
    private final JTextField m_numberQueues = new JTextField(5);
    private final JTextField m_timeLimit = new JTextField(5);
    private final JTextField m_minArrivalTime = new JTextField(5);
    private final JTextField m_maxArrivalTime = new JTextField(5);
    private final JTextField m_minServiceTime = new JTextField(5);
    private final JTextField m_maxServiceTime = new JTextField(5);
    private final JTextArea m_generatedClients = new JTextArea(10, 44);
    private final JTextArea m_output = new JTextArea(30, 50);

    public QueueView()
    {
        m_generatedClients.setEditable(false);
        m_generatedClients.setLineWrap(true);
        m_generatedClients.setWrapStyleWord(false);
        m_output.setEditable(false);
        m_output.setLineWrap(true);
        m_output.setWrapStyleWord(false);
        m_numberClients.setBackground(new Color(0xff8080));
        m_numberQueues.setBackground(new Color(0xff8080));
        m_timeLimit.setBackground(new Color(0xff8080));
        m_minArrivalTime.setBackground(new Color(0xff8080));
        m_maxArrivalTime.setBackground(new Color(0xff8080));
        m_minServiceTime.setBackground(new Color(0xff8080));
        m_maxServiceTime.setBackground(new Color(0xff8080));
        m_test.setBackground(new Color(0xaa00ff));
        m_open.setBackground(new Color(0xff9a00));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Queues Simulator - MVC");

        JPanel clients = new JPanel();
        clients.setLayout(new FlowLayout(FlowLayout.CENTER));
        clients.setBackground(new Color(0xccff66));
        clients.add(new JLabel("Number of clients: "));
        clients.add(m_numberClients);

        JPanel queues = new JPanel();
        queues.setLayout(new FlowLayout(FlowLayout.CENTER));
        queues.setBackground(new Color(0xccff66));
        queues.add(new JLabel("Number of queues: "));
        queues.add(m_numberQueues);

        JPanel time = new JPanel();
        time.setLayout(new FlowLayout(FlowLayout.CENTER));
        time.setBackground(new Color(0xccff66));
        time.add(new JLabel("Time limit: "));
        time.add(m_timeLimit);


        JPanel arrivalTime = new JPanel();
        arrivalTime.setLayout(new FlowLayout(FlowLayout.CENTER));
        arrivalTime.setBackground(new Color(0xccff66));
        arrivalTime.add(new JLabel("Arrival time from "));
        arrivalTime.add(m_minArrivalTime);
        arrivalTime.add(new JLabel(" to "));
        arrivalTime.add(m_maxArrivalTime);

        JPanel serviceTime = new JPanel();
        serviceTime.setLayout(new FlowLayout(FlowLayout.CENTER));
        serviceTime.setBackground(new Color(0xccff66));
        serviceTime.add(new JLabel("Service time from "));
        serviceTime.add(m_minServiceTime);
        serviceTime.add(new JLabel(" to "));
        serviceTime.add(m_maxServiceTime);

        JPanel userControls = new JPanel();
        userControls.setLayout(new BoxLayout(userControls, BoxLayout.PAGE_AXIS));
        userControls.setBackground(new Color(0xccff66));
        userControls.add(clients, CENTER_ALIGNMENT);
        userControls.add(queues, CENTER_ALIGNMENT);
        userControls.add(time, CENTER_ALIGNMENT);
        userControls.add(arrivalTime, CENTER_ALIGNMENT);
        userControls.add(serviceTime, CENTER_ALIGNMENT);
        userControls.add(m_test, CENTER_ALIGNMENT);
        userControls.add(m_open, CENTER_ALIGNMENT);

        m_output.setBackground(new Color(0x66ffff));
        JScrollPane scrollPane = new JScrollPane(m_output);
        DefaultCaret caret = (DefaultCaret)m_output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JPanel output = new JPanel();
        output.setLayout(new BoxLayout(output, BoxLayout.PAGE_AXIS));
        output.setBackground(new Color(0xccff66));
        output.add(new JLabel("Generated clients"), CENTER_ALIGNMENT);
        m_generatedClients.setBackground(new Color(0x66ffff));
        output.add(m_generatedClients, CENTER_ALIGNMENT);
        output.add(new JLabel("Simulation"), CENTER_ALIGNMENT);
        output.add(scrollPane, CENTER_ALIGNMENT);

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.setBackground(new Color(0xccff66));
        content.add(userControls);
        content.add(output);

        this.setContentPane(content);
        this.pack();
    }

    public void addTestListener(ActionListener atl)
    {
        m_test.addActionListener(atl);
    }

    public void addOpenListener(ActionListener aol) { m_open.addActionListener(aol); }

    public String getTimeLimit()
    {
        return m_timeLimit.getText();
    }

    public String getClientsNumber()
    {
        return m_numberClients.getText();
    }

    public String getQueuesNumber()
    {
        return m_numberQueues.getText();
    }

    public String getMinArrivalTime()
    {
        return m_minArrivalTime.getText();
    }

    public String getMaxArrivalTime()
    {
        return m_maxArrivalTime.getText();
    }

    public String getMinServiceTime()
    {
        return m_minServiceTime.getText();
    }

    public String getMaxServiceTime()
    {
        return m_maxServiceTime.getText();
    }

    public JTextArea getGeneratedClients()
    {
        return m_generatedClients;
    }

    public JTextArea getOutput()
    {
        return m_output;
    }

    public JButton getButton(){ return m_test; }

    public void showError(String value)
    {
        JOptionPane.showMessageDialog(this, value);
    }
}
