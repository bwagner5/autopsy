/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011-2016 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * CasePropertiesForm.java
 *
 * Created on Mar 14, 2011, 1:48:20 PM
 */
package org.sleuthkit.autopsy.casemodule;

import java.nio.file.Paths;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import org.openide.util.NbBundle;
import org.sleuthkit.autopsy.coreutils.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.actions.CallableSystemAction;

/**
 * The form where user can change / update the properties of the current case
 * metadata.
 */
class CasePropertiesForm extends javax.swing.JPanel {

    private static final long serialVersionUID = 1L;

    private Case current = null;
    private static JPanel caller;    // panel for error

    // Shrink a path to fit in targetLength (if necessary), by replaceing part
    // of the path with "...". Ex: "C:\Users\bob\...\folder\other\Image.img"
    private String shrinkPath(String path, int targetLength) {
        if (path.length() > targetLength) {
            String fill = "...";

            int partsLength = targetLength - fill.length();

            String front = path.substring(0, partsLength / 4);
            int frontSep = front.lastIndexOf(File.separatorChar);
            if (frontSep != -1) {
                front = front.substring(0, frontSep + 1);
            }

            String back = path.substring(partsLength * 3 / 4);
            int backSep = back.indexOf(File.separatorChar);
            if (backSep != -1) {
                back = back.substring(backSep);
            }
            return back + fill + front;
        } else {
            return path;
        }
    }

    /**
     * Creates new form CasePropertiesForm
     */
    CasePropertiesForm(Case currentCase, String crDate, String caseDir, Map<Long, String> imgPaths) throws CaseMetadata.CaseMetadataException {
        initComponents();
        caseNameTextField.setText(currentCase.getName());
        caseNumberTextField.setText(currentCase.getNumber());
        examinerTextField.setText(currentCase.getExaminer());
        crDateTextField.setText(crDate);
        caseDirTextArea.setText(caseDir);
        current = currentCase;

        CaseMetadata caseMetadata = currentCase.getCaseMetadata();
        if (caseMetadata.getCaseType() == Case.CaseType.SINGLE_USER_CASE) {
            tbDbName.setText(caseMetadata.getCaseDatabasePath());
        } else {
            tbDbName.setText(caseMetadata.getCaseDatabaseName());
        }
        Case.CaseType caseType = caseMetadata.getCaseType();
        tbDbType.setText(caseType.getLocalizedDisplayName());
        if (caseType == Case.CaseType.SINGLE_USER_CASE) {
            deleteCaseButton.setEnabled(true);
        } else {
            deleteCaseButton.setEnabled(false);
        }

        int totalImages = imgPaths.size();

        // create the headers and add all the rows
        // Header names are internationalized via the generated code, do not overwrite.
        String[] headers = {imagesTable.getColumnName(0),
            imagesTable.getColumnName(1)};
        String[][] rows = new String[totalImages][];

        int i = 0;
        for (long key : imgPaths.keySet()) {
            String path = imgPaths.get(key);
            String shortenPath = shrinkPath(path, 70);
            rows[i] = new String[]{shortenPath};
            i++;
        }

        // create the table inside with the imgPaths information
        DefaultTableModel model = new DefaultTableModel(rows, headers) {
            @Override
            // make the cells in the FileContentTable "read only"
            public boolean isCellEditable(int row, int column) {
                return false;
                //return column == lastColumn; // make the last column (Remove button), only the editable
            }
        };
        imagesTable.setModel(model);
    }

    /**
     * In this generated code below, there are 2 strings "Path" and "Remove"
     * that are table column headers in the DefaultTableModel. When this model
     * is generated, it puts the hard coded English strings into the generated
     * code. And then about 15 lines later, it separately internationalizes them
     * using: imagesTable.getColumnModel().getColumn(0).setHeaderValue(). There
     * is no way to prevent the GUI designer from putting the hard coded English
     * strings into the generated code. So, they remain, and are not used.
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        casePropLabel = new javax.swing.JLabel();
        caseNameLabel = new javax.swing.JLabel();
        crDateLabel = new javax.swing.JLabel();
        caseDirLabel = new javax.swing.JLabel();
        crDateTextField = new javax.swing.JTextField();
        caseNameTextField = new javax.swing.JTextField();
        updateCaseNameButton = new javax.swing.JButton();
        genInfoLabel = new javax.swing.JLabel();
        imgInfoLabel = new javax.swing.JLabel();
        OKButton = new javax.swing.JButton();
        imagesTableScrollPane = new javax.swing.JScrollPane();
        imagesTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        caseDirTextArea = new javax.swing.JTextArea();
        deleteCaseButton = new javax.swing.JButton();
        caseNumberLabel = new javax.swing.JLabel();
        examinerLabel = new javax.swing.JLabel();
        caseNumberTextField = new javax.swing.JTextField();
        examinerTextField = new javax.swing.JTextField();
        lbDbType = new javax.swing.JLabel();
        tbDbType = new javax.swing.JTextField();
        lbDbName = new javax.swing.JLabel();
        tbDbName = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        casePropLabel.setFont(casePropLabel.getFont().deriveFont(casePropLabel.getFont().getStyle() | java.awt.Font.BOLD, 24));
        casePropLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        casePropLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.casePropLabel.text")); // NOI18N

        caseNameLabel.setFont(caseNameLabel.getFont().deriveFont(caseNameLabel.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        caseNameLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.caseNameLabel.text")); // NOI18N

        crDateLabel.setFont(crDateLabel.getFont().deriveFont(crDateLabel.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        crDateLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.crDateLabel.text")); // NOI18N

        caseDirLabel.setFont(caseDirLabel.getFont().deriveFont(caseDirLabel.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        caseDirLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.caseDirLabel.text")); // NOI18N

        crDateTextField.setEditable(false);
        crDateTextField.setFont(crDateTextField.getFont().deriveFont(crDateTextField.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        crDateTextField.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.crDateTextField.text")); // NOI18N

        caseNameTextField.setFont(caseNameTextField.getFont().deriveFont(caseNameTextField.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        caseNameTextField.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.caseNameTextField.text")); // NOI18N

        updateCaseNameButton.setFont(updateCaseNameButton.getFont().deriveFont(updateCaseNameButton.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        updateCaseNameButton.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.updateCaseNameButton.text")); // NOI18N
        updateCaseNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCaseNameButtonActionPerformed(evt);
            }
        });

        genInfoLabel.setFont(genInfoLabel.getFont().deriveFont(genInfoLabel.getFont().getStyle() | java.awt.Font.BOLD, 14));
        genInfoLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.genInfoLabel.text")); // NOI18N

        imgInfoLabel.setFont(imgInfoLabel.getFont().deriveFont(imgInfoLabel.getFont().getStyle() | java.awt.Font.BOLD, 14));
        imgInfoLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.imgInfoLabel.text")); // NOI18N

        OKButton.setFont(OKButton.getFont().deriveFont(OKButton.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        OKButton.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.OKButton.text")); // NOI18N

        imagesTableScrollPane.setFont(imagesTableScrollPane.getFont().deriveFont(imagesTableScrollPane.getFont().getStyle() & ~java.awt.Font.BOLD, 11));

        imagesTable.setFont(imagesTable.getFont().deriveFont(imagesTable.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        imagesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Path", "Remove"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        imagesTable.setShowHorizontalLines(false);
        imagesTable.setShowVerticalLines(false);
        imagesTable.getTableHeader().setReorderingAllowed(false);
        imagesTable.setUpdateSelectionOnSort(false);
        imagesTableScrollPane.setViewportView(imagesTable);
        if (imagesTable.getColumnModel().getColumnCount() > 0) {
            imagesTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.imagesTable.columnModel.title0")); // NOI18N
            imagesTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.imagesTable.columnModel.title1")); // NOI18N
        }

        jScrollPane2.setFont(jScrollPane2.getFont().deriveFont(jScrollPane2.getFont().getStyle() & ~java.awt.Font.BOLD, 11));

        caseDirTextArea.setEditable(false);
        caseDirTextArea.setBackground(new java.awt.Color(240, 240, 240));
        caseDirTextArea.setColumns(20);
        caseDirTextArea.setRows(1);
        caseDirTextArea.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(caseDirTextArea);

        deleteCaseButton.setFont(deleteCaseButton.getFont().deriveFont(deleteCaseButton.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        deleteCaseButton.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.deleteCaseButton.text")); // NOI18N
        deleteCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteCaseButtonActionPerformed(evt);
            }
        });

        caseNumberLabel.setFont(caseNumberLabel.getFont().deriveFont(caseNumberLabel.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        caseNumberLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.caseNumberLabel.text")); // NOI18N

        examinerLabel.setFont(examinerLabel.getFont().deriveFont(examinerLabel.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        examinerLabel.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.examinerLabel.text")); // NOI18N

        caseNumberTextField.setEditable(false);
        caseNumberTextField.setFont(caseNumberTextField.getFont().deriveFont(caseNumberTextField.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        caseNumberTextField.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.caseNumberTextField.text")); // NOI18N

        examinerTextField.setEditable(false);
        examinerTextField.setFont(examinerTextField.getFont().deriveFont(examinerTextField.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        examinerTextField.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.examinerTextField.text")); // NOI18N

        lbDbType.setFont(lbDbType.getFont().deriveFont(lbDbType.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        lbDbType.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.lbDbType.text")); // NOI18N

        tbDbType.setEditable(false);
        tbDbType.setFont(tbDbType.getFont().deriveFont(tbDbType.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        tbDbType.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.tbDbType.text")); // NOI18N

        lbDbName.setFont(lbDbName.getFont().deriveFont(lbDbName.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        lbDbName.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.lbDbName.text")); // NOI18N

        tbDbName.setEditable(false);
        tbDbName.setFont(tbDbName.getFont().deriveFont(tbDbName.getFont().getStyle() & ~java.awt.Font.BOLD, 11));
        tbDbName.setText(org.openide.util.NbBundle.getMessage(CasePropertiesForm.class, "CasePropertiesForm.tbDbName.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(casePropLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(imagesTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(caseNameLabel)
                            .addComponent(caseNumberLabel)
                            .addComponent(examinerLabel)
                            .addComponent(caseDirLabel)
                            .addComponent(crDateLabel)
                            .addComponent(lbDbType))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(caseNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(caseNumberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(examinerTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(crDateTextField)
                            .addComponent(jScrollPane2)
                            .addComponent(tbDbType)
                            .addComponent(tbDbName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deleteCaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateCaseNameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genInfoLabel)
                            .addComponent(imgInfoLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbDbName))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(casePropLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(genInfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseNameLabel)
                    .addComponent(caseNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateCaseNameButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseNumberLabel)
                    .addComponent(caseNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(examinerLabel)
                    .addComponent(examinerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crDateLabel)
                    .addComponent(crDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(caseDirLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tbDbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDbType))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDbName)
                            .addComponent(tbDbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(imgInfoLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(deleteCaseButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imagesTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OKButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Updates the case name.
     *
     * @param evt The action event
     */
    private void updateCaseNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCaseNameButtonActionPerformed
        String oldCaseName = Case.getCurrentCase().getName();
        String newCaseName = caseNameTextField.getText();
        // check if the old and new case name is not equal
        if (!oldCaseName.equals(newCaseName)) {

            // check if the case name is empty
            if (newCaseName.trim().equals("")) {
                JOptionPane.showMessageDialog(caller,
                        NbBundle.getMessage(this.getClass(),
                                "CasePropertiesForm.updateCaseName.msgDlg.empty.msg"),
                        NbBundle.getMessage(this.getClass(),
                                "CasePropertiesForm.updateCaseName.msgDlg.empty.title"),
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // check if case Name contain one of this following symbol:
                //  \ / : * ? " < > |
                if (newCaseName.contains("\\") || newCaseName.contains("/") || newCaseName.contains(":")
                        || newCaseName.contains("*") || newCaseName.contains("?") || newCaseName.contains("\"")
                        || newCaseName.contains("<") || newCaseName.contains(">") || newCaseName.contains("|")) {
                    String errorMsg = NbBundle
                            .getMessage(this.getClass(), "CasePropertiesForm.updateCaseName.msgDlg.invalidSymbols.msg");
                    JOptionPane.showMessageDialog(caller, errorMsg,
                            NbBundle.getMessage(this.getClass(),
                                    "CasePropertiesForm.updateCaseName.msgDlg.invalidSymbols.title"),
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // ask for the confirmation first
                    String confMsg = NbBundle
                            .getMessage(this.getClass(), "CasePropertiesForm.updateCaseName.confMsg.msg", oldCaseName,
                                    newCaseName);
                    NotifyDescriptor d = new NotifyDescriptor.Confirmation(confMsg,
                            NbBundle.getMessage(this.getClass(),
                                    "CasePropertiesForm.updateCaseName.confMsg.title"),
                            NotifyDescriptor.YES_NO_OPTION, NotifyDescriptor.WARNING_MESSAGE);
                    d.setValue(NotifyDescriptor.NO_OPTION);

                    Object res = DialogDisplayer.getDefault().notify(d);
                    if (res != null && res == DialogDescriptor.YES_OPTION) {
                        // if user select "Yes"
                        String oldPath = current.getCaseMetadata().getFilePath().toString();
                        try {
                            current.updateCaseName(oldCaseName, oldPath, newCaseName, oldPath);
                        } catch (Exception ex) {
                            Logger.getLogger(CasePropertiesForm.class.getName()).log(Level.WARNING, "Error: problem updating case name.", ex); //NON-NLS
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_updateCaseNameButtonActionPerformed

    private void deleteCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteCaseButtonActionPerformed
        CallableSystemAction.get(CaseDeleteAction.class).actionPerformed(evt);
    }//GEN-LAST:event_deleteCaseButtonActionPerformed

    /**
     * Sets the listener for the OK button
     *
     * @param e The action listener
     */
    public void setOKButtonActionListener(ActionListener e) {
        OKButton.addActionListener(e);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKButton;
    private javax.swing.JLabel caseDirLabel;
    private javax.swing.JTextArea caseDirTextArea;
    private javax.swing.JLabel caseNameLabel;
    private javax.swing.JTextField caseNameTextField;
    private javax.swing.JLabel caseNumberLabel;
    private javax.swing.JTextField caseNumberTextField;
    private javax.swing.JLabel casePropLabel;
    private javax.swing.JLabel crDateLabel;
    private javax.swing.JTextField crDateTextField;
    private javax.swing.JButton deleteCaseButton;
    private javax.swing.JLabel examinerLabel;
    private javax.swing.JTextField examinerTextField;
    private javax.swing.JLabel genInfoLabel;
    private javax.swing.JTable imagesTable;
    private javax.swing.JScrollPane imagesTableScrollPane;
    private javax.swing.JLabel imgInfoLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lbDbName;
    private javax.swing.JLabel lbDbType;
    private javax.swing.JTextField tbDbName;
    private javax.swing.JTextField tbDbType;
    private javax.swing.JButton updateCaseNameButton;
    // End of variables declaration//GEN-END:variables

}
