package com.mgatelabs.piper.ui.frame;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mgatelabs.piper.Runner;
import com.mgatelabs.piper.shared.details.PlayerDefinition;
import com.mgatelabs.piper.shared.util.JsonTool;
import com.mgatelabs.piper.ui.utils.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mfuller@acteksoft.com">Michael Fuller</a>
 * Creation Date: 9/19/2017
 */
public class StartupFrame extends JFrame {

    final JFrame frame;
    private JComboBox<String> deviceComboBox;
    private JComboBox<String> viewComboBox;
    private JComboBox<String> recoveryComboBox;
    private JComboBox<String> scriptComboBox;
    private JComboBox<String> script2ComboBox;
    private JComboBox<String> mapComboBox;
    private JComboBox<String> modeComboBox;
    private JComboBox<String> actionComboBox;
    private final PlayerDefinition playerDefinition;

    public static final String PATH_DEVICES = "./devices";
    public static final String PATH_VIEWS = "./views";
    public static final String PATH_SCRIPTS = "./scripts";

    private String selectedView;
    private String selectedView2;
    private String selectedDevice;
    private String selectedScript;
    private String selectedScript2;
    private String selectedMap;
    private String selectedMode;
    private String selectedAction;

    private String postfix;

    public StartupFrame(PlayerDefinition playerDefinition, ImageIcon icon, String postfix) throws HeadlessException {
        super();
        this.setTitle("PhonePiper - " + Runner.VERSION);
        setIconImage(icon.getImage());
        frame = this;
        this.postfix = postfix;
        this.playerDefinition = playerDefinition;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setMinimumSize(new Dimension(400, 200));
        build();
    }

    public String getSelectedDevice() {
        return selectedDevice;
    }

    public String getSelectedView() {
        return selectedView;
    }

    public String getSelectedView2() {
        return selectedView2;
    }

    public void setSelectedView2(String selectedView2) {
        this.selectedView2 = selectedView2;
    }

    public String getSelectedScript() {
        return selectedScript;
    }

    public String getSelectedScript2() {
        return selectedScript2;
    }

    public void setSelectedScript2(String selectedScript2) {
        this.selectedScript2 = selectedScript2;
    }

    public String getSelectedMap() {
        return selectedMap;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    private boolean setup() {
        File devices = new File(Runner.WORKING_DIRECTORY, "./devices");
        File scripts = new File(Runner.WORKING_DIRECTORY, "./scripts");
        File views = new File(Runner.WORKING_DIRECTORY, "./views");
        return (devices.exists() && scripts.exists() && views.exists());
    }

    private void build() {

        if (!setup()) {

        } else {
            setLayout(new GridBagLayout());

            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints c2 = new GridBagConstraints();
            c2.insets = new Insets(5, 5, 5, 5);
            c2.gridwidth = 2;
            c2.fill = GridBagConstraints.HORIZONTAL;
            this.add(fieldPanel, c2);

            {
                GridBagConstraints c = new GridBagConstraints();
                JLabel label;

                // MODE

                label = new JLabel("Mode");
                c.gridx = 0;
                c.gridy = 0;
                c.ipadx = 4;
                c.gridwidth = 1;
                c.weightx = 0;
                fieldPanel.add(label, c);

                String[] modeArray = new String[]{Constants.MODE_SCRIPT, Constants.MODE_MAP, Constants.MODE_DEVICE, Constants.MODE_VIEW};
                modeComboBox = new JComboBox<>(modeArray);
                c.gridx = 1;
                c.gridy = 0;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 1;
                fieldPanel.add(modeComboBox, c);

                // ACTION

                label = new JLabel("Action");
                c.gridx = 0;
                c.gridy = 1;
                c.ipadx = 4;
                c.gridwidth = 1;
                c.weightx = 0;
                fieldPanel.add(label, c);

                String[] actionArray = new String[]{Constants.ACTION_RUN, Constants.ACTION_EDIT, Constants.ACTION_CREATE, Constants.ACTION_DELETE};
                actionComboBox = new JComboBox<>(actionArray);
                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 2;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 1;
                fieldPanel.add(actionComboBox, c);

                // DEVICES

                label = new JLabel("Devices");
                c.gridx = 0;
                c.gridy = 2;
                c.ipadx = 4;
                c.gridwidth = 1;
                c.weightx = 0;
                fieldPanel.add(label, c);

                deviceComboBox = new JComboBox<>(listJsonFilesIn(new File(Runner.WORKING_DIRECTORY, "./devices")));
                c.gridx = 1;
                c.gridy = 2;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 2;
                c.weightx = 1;
                fieldPanel.add(deviceComboBox, c);

                // VIEWS

                label = new JLabel("Views");
                c.gridx = 0;
                c.gridy = 3;
                c.ipadx = 4;
                c.gridwidth = 1;
                c.weightx = 0;
                fieldPanel.add(label, c);

                viewComboBox = new JComboBox<>(listFoldersFilesIn(new File(Runner.WORKING_DIRECTORY, "./views")));
                c.gridx = 1;
                c.gridy = 3;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 1;
                c.weightx = 1;
                fieldPanel.add(viewComboBox, c);

                recoveryComboBox = new JComboBox<>(listFoldersFilesIn(new File(Runner.WORKING_DIRECTORY, "./views")));
                c.gridx = 2;
                c.gridy = 3;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 1;
                c.weightx = 1;
                fieldPanel.add(recoveryComboBox, c);

                // SCRIPTS

                label = new JLabel("Scripts");
                c.gridx = 0;
                c.gridy = 4;
                c.ipadx = 4;
                c.gridwidth = 1;
                c.weightx = 0;
                fieldPanel.add(label, c);

                scriptComboBox = new JComboBox<>(listJsonFilesIn(new File(Runner.WORKING_DIRECTORY, "./scripts")));

                c.gridx = 1;
                c.gridy = 4;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 1;
                c.weightx = 1;
                fieldPanel.add(scriptComboBox, c);

                script2ComboBox = new JComboBox<>(listJsonFilesIn(new File(Runner.WORKING_DIRECTORY, "./scripts")));
                c.gridx = 2;
                c.gridy = 4;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 1;
                c.weightx = 1;
                fieldPanel.add(script2ComboBox, c);

                // MAPS

                label = new JLabel("Maps");
                c.gridx = 0;
                c.gridy = 5;
                c.ipadx = 4;
                c.gridwidth = 1;
                c.weightx = 0;
                fieldPanel.add(label, c);

                mapComboBox = new JComboBox<>(); // listJsonFilesIn(new File(Runner.WORKING_DIRECTORY,"./maps"))
                c.gridx = 1;
                c.gridy = 5;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridwidth = 2;
                c.weightx = 1;
                fieldPanel.add(mapComboBox, c);
            }

            // Actions

            JButton close = new JButton("Close");
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedAction = null;
                    selectedMode = null;
                    frame.dispose();
                }
            });
            c2.gridx = 0;
            c2.gridy = 1;
            c2.gridwidth = 1;
            c2.fill = GridBagConstraints.HORIZONTAL;
            c2.weightx = 1;
            c2.insets = new Insets(10, 5, 10, 5);
            this.add(close, c2);

            JButton go = new JButton("Start");
            go.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedScript = (String) scriptComboBox.getSelectedItem();
                    selectedScript2 = (String) script2ComboBox.getSelectedItem();
                    selectedDevice = (String) deviceComboBox.getSelectedItem();
                    selectedView = (String) viewComboBox.getSelectedItem();
                    selectedView2 = (String) recoveryComboBox.getSelectedItem();
                    selectedMap = (String) mapComboBox.getSelectedItem();
                    selectedAction = (String) actionComboBox.getSelectedItem();
                    selectedMode = (String) modeComboBox.getSelectedItem();

                    final StartSelections selections = new StartSelections();

                    selections.setPostfix(postfix);

                    selections.setSelectedScript(nullToEmpty(selectedScript));
                    selections.setSelectedScript2(nullToEmpty(selectedScript2));
                    selections.setSelectedDevice(nullToEmpty(selectedDevice));
                    selections.setSelectedView(nullToEmpty(selectedView));
                    selections.setSelectedView2(nullToEmpty(selectedView2));
                    selections.setSelectedMap(nullToEmpty(selectedMap));
                    selections.setSelectedAction(nullToEmpty(selectedAction));
                    selections.setSelectedMode(nullToEmpty(selectedMode));

                    selections.save();

                    frame.dispose();
                }
            });
            c2.gridx = 1;
            c2.gridy = 1;
            this.add(go, c2);

        }

        StartSelections startSelections = StartSelections.read(postfix);

        if (startSelections != null) {
            updateList(modeComboBox, nullToEmpty(startSelections.getSelectedMode()));
            updateList(actionComboBox, nullToEmpty(startSelections.getSelectedAction()));
            updateList(deviceComboBox, nullToEmpty(startSelections.getSelectedDevice()));
            updateList(viewComboBox, nullToEmpty(startSelections.getSelectedView()));
            updateList(recoveryComboBox, nullToEmpty(startSelections.getSelectedView2()));
            updateList(scriptComboBox, nullToEmpty(startSelections.getSelectedScript()));
            updateList(script2ComboBox, nullToEmpty(startSelections.getSelectedScript2()));
            updateList(mapComboBox, nullToEmpty(startSelections.getSelectedMap()));
        }

        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void updateList(JComboBox<String> list, String selection) {
        list.setSelectedItem(selection);
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    public static String[] listJsonFilesIn(File dir) {
        List<String> itemList = Lists.newArrayList();
        itemList.add("");
        for (File f : dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json") && !name.startsWith("_");
            }
        })) {
            itemList.add(f.getName().substring(0, f.getName().length() - 5));
        }
        Collections.sort(itemList);
        String[] itemArray = new String[itemList.size()];
        itemList.toArray(itemArray);
        return itemArray;
    }


    public static String[] listFoldersFilesIn(File dir) {
        List<String> itemList = Lists.newArrayList();
        itemList.add("");
        for (File f : dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        })) {
            itemList.add(f.getName());
        }
        Collections.sort(itemList);
        String[] itemArray = new String[itemList.size()];
        itemList.toArray(itemArray);
        return itemArray;
    }

    public static List<String> arrayToList(String[] items) {
        List<String> list = Lists.newArrayList();
        for (String item : items) {
            list.add(item);
        }
        return list;
    }

    public static class StartSelections {

        private String selectedView;
        private String selectedView2;
        private String selectedDevice;
        private String selectedScript;
        private String selectedScript2;
        private String selectedMap;
        private String selectedMode;
        private String selectedAction;

        @JsonIgnore
        private String postfix;

        public String getPostfix() {
            return postfix;
        }

        public void setPostfix(String postfix) {
            this.postfix = postfix;
        }

        public String getSelectedView() {
            return selectedView;
        }

        public String getSelectedView2() {
            return selectedView2;
        }

        public void setSelectedView2(String selectedView2) {
            this.selectedView2 = selectedView2;
        }

        public void setSelectedView(String selectedView) {
            this.selectedView = selectedView;
        }

        public String getSelectedDevice() {
            return selectedDevice;
        }

        public void setSelectedDevice(String selectedDevice) {
            this.selectedDevice = selectedDevice;
        }

        public String getSelectedScript() {
            return selectedScript;
        }

        public void setSelectedScript(String selectedScript) {
            this.selectedScript = selectedScript;
        }

        public String getSelectedScript2() {
            return selectedScript2;
        }

        public void setSelectedScript2(String selectedScript2) {
            this.selectedScript2 = selectedScript2;
        }

        public String getSelectedMap() {
            return selectedMap;
        }

        public void setSelectedMap(String selectedMap) {
            this.selectedMap = selectedMap;
        }

        public String getSelectedMode() {
            return selectedMode;
        }

        public void setSelectedMode(String selectedMode) {
            this.selectedMode = selectedMode;
        }

        public String getSelectedAction() {
            return selectedAction;
        }

        public void setSelectedAction(String selectedAction) {
            this.selectedAction = selectedAction;
        }

        public static StartSelections read(final String postfix) {
            File selectionFile = getFileFor(postfix);
            if (selectionFile.exists()) {
                ObjectMapper objectMapper = JsonTool.getInstance();
                try {
                    final StartSelections selection = objectMapper.readValue(selectionFile, StartSelections.class);
                    selection.setPostfix(postfix);
                    return selection;
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public static File getFileFor(final String postfix) {
            return new File(Runner.WORKING_DIRECTORY, "./selections" + (StringUtils.isNotBlank(postfix) ? ("-" + postfix) : "") + ".json");
        }

        public static boolean exists(final String postfix) {
            return getFileFor(postfix).exists();
        }

        public boolean save() {
            File selectionFile = getFileFor(getPostfix());
            final ObjectMapper objectMapper = JsonTool.getInstance();
            try {
                objectMapper.writeValue(selectionFile, this);
                return true;
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
