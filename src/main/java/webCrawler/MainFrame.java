package webCrawler;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFrame extends JFrame {

    JLabel labelStartUrl = new JLabel("Start URL: ");
    JLabel labelWorkers = new JLabel("Workers: ");
    JLabel labelMaximumDepth = new JLabel("Maximum depth: ");
    JLabel labelTimeLimit = new JLabel("Time limit: ");
    JLabel labelElapsedTime = new JLabel("Elapsed time: ");
    JLabel labelCurrentTime = new JLabel("0:00");
    JLabel labelParsedPages = new JLabel("Parsed pages: ");
    JLabel labelCurrentParsedPages = new JLabel("0");
    JLabel labelExport = new JLabel("Export: ");
    JLabel labelSeconds = new JLabel("seconds");

    JTextField fieldStartUrl = new JTextField();
    JTextField fieldNumOfWorkers = new JTextField();
    JTextField fieldMaximumDepth = new JTextField();
    JTextField fieldTimeLimit = new JTextField();
    JTextField fieldExport = new JTextField();

    JToggleButton buttonRunStop = new JToggleButton("Run");
    JButton buttonExport = new JButton("Save");

    JCheckBox checkBoxDepthEnabled = new JCheckBox("Enabled");
    JCheckBox checkBoxTimeLimit = new JCheckBox("Enabled");

    Pattern htmlPagePattern = Pattern.compile("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$");

    List<Link> list;

    volatile private boolean  timerOn=true;

    MainFrame(String name){
        super(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        fieldNumOfWorkers.setText("1");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill=GridBagConstraints.HORIZONTAL;

        {
            Insets insets = new Insets(3,3,3,3);
            constraints.insets=insets;

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelStartUrl, constraints);

            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.gridx = 2;
            constraints.gridy = 0;
            constraints.gridheight = 1;
            constraints.gridwidth = 4;

            add(fieldStartUrl, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 6;
            constraints.gridy = 0;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            add(buttonRunStop, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelWorkers, constraints);

            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.gridx = 2;
            constraints.gridy = 1;
            constraints.gridheight = 1;
            constraints.gridwidth = 5;
            add(fieldNumOfWorkers, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelMaximumDepth, constraints);

            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.gridx = 2;
            constraints.gridy = 2;
            constraints.gridheight = 1;
            constraints.gridwidth = 4;
            add(fieldMaximumDepth, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 6;
            constraints.gridy = 2;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            add(checkBoxDepthEnabled, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelTimeLimit, constraints);

            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.gridx = 2;
            constraints.gridy = 3;
            constraints.gridheight = 1;
            constraints.gridwidth = 3;
            add(fieldTimeLimit, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridy = 3;
            constraints.gridx = 5;
            constraints.gridwidth = 1;
            constraints.gridheight = 1;
            add(labelSeconds, constraints);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 6;
            constraints.gridy = 3;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            add(checkBoxTimeLimit, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelElapsedTime, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 2;
            constraints.gridy = 4;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            add(labelCurrentTime, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 5;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelParsedPages, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 2;
            constraints.gridy = 5;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            add(labelCurrentParsedPages, constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 0;
            constraints.gridy = 6;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            add(labelExport,constraints);

            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.gridx = 2;
            constraints.gridy = 6;
            constraints.gridheight = 1;
            constraints.gridwidth = 4;
            add(fieldExport,constraints);

            constraints.weightx = 0;
            constraints.weighty = 1;
            constraints.gridx = 6;
            constraints.gridy = 6;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            add(buttonExport,constraints);
        }

        buttonRunStop.addActionListener((x)->{
            System.out.println("Action perfomed");
            if(buttonRunStop.getModel().isSelected()){
                Matcher matcher = htmlPagePattern.matcher(fieldStartUrl.getText());
                if(matcher.matches()){
                    startTimer();
                    startParsing(fieldStartUrl.getText());
                }
            }
        });

        buttonExport.addActionListener((x)->{
            export();
        });

        System.out.println(Thread.currentThread().getName());

        setVisible(true);
    }

    private void startParsing(String url){
        SwingWorker<Void,Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                int numOfWorkers=1;
                try{
                    numOfWorkers = Integer.parseInt(fieldNumOfWorkers.getText());
                    if(numOfWorkers>50){
                        numOfWorkers=50;
                    }
                }catch (Exception e){
                    System.out.println("Num of workers has a bad value");
                }
                HtmlParser parser = new HtmlParser(fieldStartUrl.getText(),numOfWorkers);

                if(checkBoxTimeLimit.isSelected()){
                    System.out.println("time check enabled");
                    try{
                        int timeLimit=Integer.parseInt(fieldTimeLimit.getText());
                        if(timeLimit<=0){
                            throw new Exception();
                        }
                        parser.setTimeIsEnabled(timeLimit);
                    }catch (Exception e){
                        System.out.println("Bad time");
                    }
                }

                if(checkBoxDepthEnabled.isSelected()){
                    try{
                        int depth = Integer.parseInt(fieldMaximumDepth.getText());
                        if(depth<1){
                            throw new Exception();
                        }
                        parser.setDepth(depth);
                    }catch (Exception e ){
                        System.out.println("Bad depth");
                    }
                }

                new Thread(() -> {
                    try {
                        parser.startParsing();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                while(timerOn&&buttonRunStop.getModel().isSelected()){
                    labelCurrentParsedPages.setText(String.valueOf(parser.getPagesParsed()));
                }

                parser.setStopped();
                list = parser.getLinks();
                System.out.println("success");

                buttonRunStop.setSelected(false);
                return null;
            }
        };
        swingWorker.execute();
    }

    private void startTimer(){
        SwingWorker<Void,Void> swingTimerWorker = new SwingWorker<Void, Void>() {
            int seconds=0;
            int tenths=0;
            int hundredths=0;
            @Override
            protected Void doInBackground() throws Exception {
                timerOn=true;
                while (timerOn&&buttonRunStop.getModel().isSelected()) {
                        labelCurrentTime.setText(seconds +":"+ tenths + hundredths);
                        if (checkBoxTimeLimit.isSelected()&&seconds == Integer.parseInt(fieldTimeLimit.getText())) {
                            timerOn = false;
                        }
                        try {
                            Thread.sleep(9);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    hundredths++;
                        if(hundredths==10){
                            hundredths=0;
                            tenths++;
                        }
                        if(tenths==10){
                            tenths=0;
                            seconds++;
                        }
                }
                return null;
            }
        };
        swingTimerWorker.execute();
    }

    private void export(){
        System.out.println("export started");
        File file = new File(fieldExport.getText());
        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(Link link:list){
                writer.write(link.toString()+"\n");
            }
            writer.close();
            System.out.println("saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}