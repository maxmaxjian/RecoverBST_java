import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class SolutionTest {
    private TreeNode input;
    private TreeNode expected;
    private Solution soln = new Solution1();

    public SolutionTest(TreeNode input, TreeNode output) {
        this.input = input;
        this.expected = output;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
                {createTree("1,3,#,#,2"), createTree("3,1,#,#,2")},
                {createTree("3,1,4,#,#,2"), createTree("2,1,4,#,#,3")}
        });
    }

    private static TreeNode createTree(String s) {
        String[] strs = s.split(",");
        TreeNode[] nodes = new TreeNode[strs.length];
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].equals("#")) {
                nodes[i] = new TreeNode(Integer.valueOf(strs[i]));
            } else {
                nodes[i] = null;
            }
            if (i > 0) {
                if (i%2 == 1) {
                    nodes[i/2].left = nodes[i];
                } else {
                    nodes[i/2-1].right = nodes[i];
                }
            }
        }

        return nodes[0];
    }

    @Test
    public void test() {
        input = soln.recoverTree(input);
        assert(checkEquals(expected, input));
    }

    private boolean checkEquals(TreeNode expected, TreeNode actual) {
        List<TreeNode> list1 = new ArrayList<>(), list2 = new ArrayList<>();
        preorder(expected, list1);
        preorder(actual, list2);

        if (list1.size() == list2.size()) {
            for (int i = 0; i < list1.size(); i++) {
                if (list1.get(i).val != list2.get(i).val) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void preorder(TreeNode root, List<TreeNode> list) {
        if (root != null) {
            list.add(root);
            preorder(root.left, list);
            preorder(root.right, list);
        }
    }
}