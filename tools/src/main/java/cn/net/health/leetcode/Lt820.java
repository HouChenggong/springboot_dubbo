package cn.net.health.leetcode;

import java.util.*;

public class Lt820 {
    public static int minimumLengthEncoding(String[] words) {
        Set<String> good = new HashSet(Arrays.asList(words));
        for (String word : words) {
            for (int k = 1; k < word.length(); ++k)
                good.remove(word.substring(k));
        }

        int ans = 0;
        for (String word : good)
            ans += word.length() + 1;
        return ans;
    }
    public static int minimumLengthEncoding2(String[] words) {
        int N = words.length;
        // 反转每个单词
        String[] reversed_words = new String[N];
        for (int i = 0; i < N; i++) {
            String word = words[i];
            String rword = new StringBuilder(word).reverse().toString();
            reversed_words[i] = rword;
        }
        // 字典序排序
        Arrays.sort(reversed_words);

        int res = 0;
        for (int i = 0; i < N; i++) {
            if (i+1 < N && reversed_words[i+1].startsWith(reversed_words[i])) {
                // 当前单词是下一个单词的前缀，丢弃
            } else {
                res += reversed_words[i].length() + 1; // 单词加上一个 '#' 的长度
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"time", "me", "tell", "time", "asme"};
        System.out.println(minimumLengthEncoding(arr));
        System.out.println(minimumLengthEncoding2(arr));
        System.out.println(minimumLengthEncodingTree(arr));

    }

    public static int minimumLengthEncodingTree(String[] words) {
        TrieNode trie = new TrieNode();
        Map<TrieNode, Integer> nodes = new HashMap();

        for (int i = 0; i < words.length; ++i) {
            String word = words[i];
            TrieNode cur = trie;
            for (int j = word.length() - 1; j >= 0; --j)
                cur = cur.get(word.charAt(j));
            nodes.put(cur, i);
        }

        int ans = 0;
        for (TrieNode node: nodes.keySet()) {
            if (node.count == 0)
                ans += words[nodes.get(node)].length() + 1;
        }
        return ans;

    }
}



class TrieNode {
    TrieNode[] children;
    int count;
    TrieNode() {
        children = new TrieNode[26];
        count = 0;
    }
    public TrieNode get(char c) {
        if (children[c - 'a'] == null) {
            children[c - 'a'] = new TrieNode();
            count++;
        }
        return children[c - 'a'];
    }
}



