import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Solution1 implements Solution {

    Map<TreeNode, TreeNode> map = new HashMap<>();

    @Override
    public TreeNode recoverTree(TreeNode root) {
        TreeNode first, second = null;
        TreeNode prev = root, curr;
        while (prev.left != null) {
            prev = prev.left;
        }
        curr = inorderNext(root, prev);
        while (curr != null && prev.val < curr.val) {
            prev = curr;
            curr = inorderNext(root, prev);
        }
        first = prev;

        prev = curr;
        curr = inorderNext(root, prev);
        while (curr != null && prev.val < curr.val) {
            prev = curr;
            curr = inorderNext(root, prev);
        }
        if (curr == null) {
            second = inorderNext(root, first);
        } else {
            second = curr;
        }
//        System.out.println("first -> " + first.val);
//        System.out.println("second -> " + second.val);

        TreeNode firstParent = getParent(root, first), secondParent = getParent(root, second);
        if (second.left == first) {
            if (secondParent == null) {
                root = first;
            }
            second.left = first.left;
            first.left = second;
            TreeNode tmp = first.right;
            first.right = second.right;
            second.right = tmp;
        } else if (first.right == second) {
          if (firstParent == null) {
              root = second;
          }
          first.right = second.right;
          second.right = first;
          TreeNode tmp = first.left;
          first.left = second.left;
          second.left = tmp;
        } else {
            if (firstParent != null && secondParent != null) {
                if (second == secondParent.left) {
                    secondParent.left = first;
                } else {
                    secondParent.right = first;
                }
                if (first == firstParent.left) {
                    firstParent.left = second;
                } else {
                    firstParent.right = second;
                }
            } else if (firstParent == null) {
                root = second;
                if (second == secondParent.left) {
                    secondParent.left = first;
                } else {
                    secondParent.right = first;
                }
            } else {
                root = first;
                if (first == firstParent.left) {
                    firstParent.left = second;
                } else {
                    firstParent.right = second;
                }
            }
            TreeNode tmp = first.left;
            first.left = second.left;
            second.left = tmp;
            tmp = first.right;
            first.right = second.right;
            second.right = tmp;
        }
        return root;
    }

    private TreeNode inorderNext(TreeNode root, TreeNode curr) {
        if (curr.right != null) {
            TreeNode next = curr.right;
            while (next.left != null) {
                next = next.left;
            }
            return next;
        } else {
            TreeNode parent = getParent(root, curr);
            if (parent == null) {
                TreeNode next = curr.right;
                if (next != null) {
                    while (next.left != null) {
                        next = next.left;
                    }
                }
                return next;
            } else {
                if (curr == parent.left) {
                    return parent;
                } else {
                    TreeNode gparent = getParent(root, parent);
                    while (gparent != null && parent != gparent.left) {
                        parent = gparent;
                        gparent = getParent(root, parent);
                    }
                    return gparent;
                }
            }
        }
    }

    private TreeNode getParent(TreeNode root, TreeNode curr) {
        if (!map.containsKey(curr)) {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode p = stack.pop();
                if (p != null) {
                    if (curr == p.left || curr == p.right) {
                        map.put(curr, p);
                        break;
                    } else {
                        stack.push(p.left);
                        stack.push(p.right);
                    }
                }
            }
            if (!map.containsKey(curr)) {
                map.put(curr, null);
            }
        }
        return map.get(curr);
    }
}
