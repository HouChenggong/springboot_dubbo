package cn.net.health.util.file;


import cn.net.health.util.response.ServerResponse;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * @author xiyou
 */
public class GetDirTreeUtils {
    /**
     * 递归得到文件树
     *
     * @param relativePath 相对路径
     * @param root
     * @param pathHeader   头路径
     */
    public static void getTree(String relativePath, Node root, String pathHeader) {
        //全路径
        String allPath = pathHeader + "/" + relativePath;
        File file = new File(allPath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                Node cur = new Node(relativePath + "/" + f.getName(), f.getName(), true);
                root.children.add(cur);
                getTree(cur.relativePath, cur, pathHeader);
            } else {
                root.children.add(
                        new Node(relativePath + "/" + f.getName(), f.getName(), false)
                );
            }
        }
    }

    /**
     * 根据头路径和相对路径获取文件树
     *
     * @param relativePath
     * @param pathHeader
     * @return
     */
    public static ServerResponse generateGetTree(String relativePath, String pathHeader) {
        relativePath = PathToTreeUtils.zhuanYi(relativePath);
        pathHeader = PathToTreeUtils.zhuanYi(pathHeader);
        String allPath = pathHeader + "/" + relativePath;
        File file = new File(allPath);
        if (!file.exists()) {
            return ServerResponse.createBySuccessMessage("当前文件不存在");
        }
        Node root = new Node(relativePath, file.getName(), true);
        getTree(relativePath, root, pathHeader);
        return ServerResponse.createBySuccess(root);
    }

    /**
     * 根据头路径和相对路径获取文件树，带有全路径
     *
     * @param relativePath
     * @param pathHeader
     * @return
     */
    public static ServerResponse generateGetTreeWithAllPath(String relativePath, String pathHeader) {
        relativePath = PathToTreeUtils.zhuanYi(relativePath);
        pathHeader = PathToTreeUtils.zhuanYi(pathHeader);
        String allPath = pathHeader + "/" + relativePath;
        File file = new File(allPath);
        if (!file.exists()) {
            return ServerResponse.createBySuccessMessage("当前文件不存在");
        }
        Node root = new Node(allPath, relativePath, file.getName(), true);
        getTreeWithAllPath(relativePath, root, pathHeader);
        return ServerResponse.createBySuccess(root);
    }


    /**
     * 递归得到文件树,树里面带有每一个文件的全路径
     *
     * @param relativePath 相对路径
     * @param root
     * @param pathHeader   头路径
     */
    public static void getTreeWithAllPath(String relativePath, Node root, String pathHeader) {
        //全路径
        String allPath = pathHeader + "/" + relativePath;
        File file = new File(allPath);
        File[] files = file.listFiles();
        for (File f : files) {
            allPath += "/" + f.getName();
            if (f.isDirectory()) {
                Node cur = new Node(allPath, relativePath + "/" + f.getName(), f.getName(), true);
                root.children.add(cur);
                getTreeWithAllPath(cur.relativePath, cur, pathHeader);
            } else {
                root.children.add(
                        new Node(allPath, relativePath + "/" + f.getName(), f.getName(), false)
                );
            }
        }
    }

    public static void main(String[] args) {
        String path = "D:\\test\\5e5b6ed01a9a14794dc35eab";

        ServerResponse root = generateGetTree("aa", path);
        ServerResponse root2 = generateGetTreeWithAllPath("aa", path);
//        d.print_path(root);
        JSONObject object = new JSONObject();
        object.put("root", root);
        object.put("root2", root2);
        System.out.println(object.toString());
    }


}

class Node {

    public String relativePath;
    /**
     * 全路径
     */
    public String allPath;
    public String name;
    public boolean isDir;
    public ArrayList<Node> children = new ArrayList<>();

    public Node(String relativePath, String name, boolean isDir) {

        this.relativePath = relativePath;
        this.name = name;
        this.isDir = isDir;
    }

    public Node(String allPath, String relativePath, String name, boolean isDir) {
        this.allPath = allPath;
        this.relativePath = relativePath;
        this.name = name;
        this.isDir = isDir;
    }
}

