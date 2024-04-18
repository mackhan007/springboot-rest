package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode {
    HashMap<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;
    int count;

    /**
     * @param word : string to add to the trie for search later
     */
    public void addName(String word) {
        TrieNode current = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.isEndOfWord = true;
        current.count++;
    }

    /**
     * @param car  : character to add to the string
     * @param data : list of strings to add the character to
     * @return
     */
    private List<String> updateData(Character car, List<String> data) {
        List<String> result = new ArrayList<>();

        for (String name : data) {
            result.add(car + name);
        }
        return result;
    }

    /**
     * @param prefix      : string for search
     * @param currentNode : used for recursion
     * @return list of names that start with the prefix
     */
    public List<String> getNames(String prefix, TrieNode currentNode) {
        TrieNode current;
        List<String> result = new ArrayList<>();

        if (currentNode == null) {
            current = this;
        } else {
            current = currentNode;
        }

        if (prefix.length() > 0) {
            Character ch = prefix.charAt(0);
            TrieNode next = current.children.get(ch);

            if (next == null) {
                return new ArrayList<>();
            } else {
                result.addAll(updateData(ch, getNames(prefix.substring(1), next)));

                if (prefix.length() == 1 && next.isEndOfWord) {
                    result.add(ch + "");
                }
                return result;
            }
        }

        for (char ch : current.children.keySet()) {
            TrieNode next = current.children.get(ch);
            result.addAll(updateData(ch, getNames("", next)));

            if (next.isEndOfWord) {
                result.add(String.valueOf(ch));
            }

        }
        return result;

    }

}
