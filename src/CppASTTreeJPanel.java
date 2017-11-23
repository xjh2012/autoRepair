import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.parser.ParserLanguage;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;

/**
 * Created by xjh on 2017/10/30.
 */
public class CppASTTreeJPanel extends JPanel implements TreeSelectionListener {
    private JTree tree;
    private RSyntaxTextArea textArea;

    public CppASTTreeJPanel() {
        super(new GridLayout(1, 0));
        System.out.println( System.getProperty("user.dir") + File.separator + "testFiles" + File.separator);
        //Create the nodes.语法树根节点
        String sourceFile = //"C:\\Users\\qiujing\\Downloads\\cocos2d-x-3\\cocos2d-x-3\\cocos\\editor-support\\spine\\Slot.c";
                System.getProperty("user.dir") + File.separator + "testFiles" + File.separator + "exception1.c";
        IASTTranslationUnit tu = CppParser.parse(sourceFile, ParserLanguage.C, false);

        //遍历语法树，添加所有子节点进top
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("TU");
        createNodes(top, tu);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        textArea.setText(T.readFile(sourceFile));
        textArea.setEditable(false);
        textArea.select(0, 0);
        textArea.setSelectionColor(Color.RED);

        RTextScrollPane sp = new RTextScrollPane(textArea);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(sp);

        Dimension minimumSize = new Dimension(100, 50);
        sp.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(150);
        splitPane.setPreferredSize(new Dimension(800, 640));

        //Add the split pane to this panel.
        add(splitPane);
    }

    /**
     * Required by TreeSelectionListener interface.
     */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (nodeInfo instanceof NodeInfo)
            displayNode((NodeInfo) nodeInfo);
    }

    private void displayNode(NodeInfo node) {
        int start = node.node.getFileLocation().getNodeOffset();
        int end = start + node.node.getFileLocation().getNodeLength();
        textArea.select(start, end);
    }

    private class NodeInfo {
        IASTNode node;

        NodeInfo(IASTNode node) {
            this.node = node;
        }

        public String toString() {
            return node.getClass().getSimpleName();
        }
    }

    //create childNode, traverse the tree.
    private void createNodes(DefaultMutableTreeNode top, IASTNode node) {
        DefaultMutableTreeNode _node = new DefaultMutableTreeNode(new NodeInfo(node));
        top.add(_node);
        for (IASTNode child : node.getChildren()) {
            createNodes(_node, child);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        boolean useSystemLookAndFeel = false;
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }

        //Create and set up the window.
        JFrame frame = new JFrame("CppASTTree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new CppASTTreeJPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(CppASTTreeJPanel::createAndShowGUI);
    }
}
