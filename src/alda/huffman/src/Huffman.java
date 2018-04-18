/*

    erik hörnström, erho7892
    jens plate, jepl4052
    mikael sundström, misu5792

 */

package alda.huffman.src;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Huffman {

    private Map<Character, Integer> charMap;
    private PriorityQueue<HuffNode> pq;
    private HuffNode root;
    private Map<Character, String> huffMap;

    public String compress(String message) {

        charMap = buildCharMap(message);
        pq = createNodePq(charMap);
        root = buildHuffmanTree(pq);
        huffMap = assignOnesAndZeros(root);

        return createEncodedString(message, huffMap);
    }

    public String decompress(String compressed) {
        StringBuilder strB = new StringBuilder();
        HuffNode current = root;
        int i = 0;


        while (i < compressed.length()) {

            while (current.notALeaf()) {

                char bit = compressed.charAt(i);

                if (bit == '1') {
                    current = current.right;
                } else if (bit == '0') {
                    current = current.left;
                } else {
                    throw new IllegalArgumentException("Invalid bit" + bit);
                }
                i++;
            }

            strB.append(current.c);
            current = root;
        }

        return strB.toString();
    }

    private Map<Character, Integer> buildCharMap(String message) {

        char[] chars = message.toCharArray();
        Map<Character, Integer> charMap = new HashMap<>();

        for (int i = 0; i < chars.length; i++) {

            if (charMap.containsKey(chars[i])) {
                charMap.put(chars[i], charMap.get(chars[i]) + 1);
            }
            else {
                charMap.put(chars[i], 1);
            }
        }
        return charMap;
    }

    private PriorityQueue<HuffNode> createNodePq(Map<Character, Integer> charMap) {

        PriorityQueue<HuffNode> pq = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : charMap.entrySet()) {

            pq.add(new HuffNode(null, null, entry.getKey(), entry.getValue()));
        }
        return pq;
    }

    public HuffNode buildHuffmanTree(PriorityQueue<HuffNode> pq) {

        if (pq.size() == 1) {

            pq.add(new HuffNode(null, null, pq.poll().freq));
        }
        else {

            for (int i = pq.size(); i > 1; i--) {

                HuffNode n1 = pq.poll();
                HuffNode n2 = pq.poll();

                HuffNode p = new HuffNode(n1, n2, (n1.freq + n2.freq));

                pq.add(p);
            }
        }

        return pq.poll();
    }

    private Map<Character, String> assignOnesAndZeros(HuffNode root) {
        Map<Character, String> huffMap = new HashMap<>();
        assignHuffValues("", root, huffMap);
        return huffMap;
    }

    private void assignHuffValues(String code, HuffNode n, Map<Character, String> huffMap) {

        if (n.notALeaf()) {

            assignHuffValues(code + "0", n.left, huffMap);
            assignHuffValues(code + "1", n.right, huffMap);

        } else {
            huffMap.put(n.c, code);
        }
    }

    private String createEncodedString(String message, Map<Character, String> map) {
        StringBuilder encodedResult = new StringBuilder();
        for (char c : message.toCharArray()) {
            encodedResult.append(map.get(c));
        }
        return encodedResult.toString();
    }

    public String readFromFile(String filename) throws IOException {
        byte[] input = Files.readAllBytes(Paths.get(filename));
        return new String(input);
    }

    class HuffNode implements Comparable<HuffNode> {
        HuffNode left;
        HuffNode right;
        char c;
        int freq;

        public HuffNode(HuffNode left, HuffNode right, char c, int freq) {
            this.left = left;
            this.right = right;
            this.c = c;
            this.freq = freq;
        }

        public HuffNode(HuffNode left, HuffNode right, int freq) {
            this.left = left;
            this.right = right;
            this.freq = freq;
        }

        public boolean notALeaf() {
            if (left != null && right != null) {
                return true;
            }
            return false;
        }

        @Override
        public int compareTo(HuffNode o) {
            return this.freq - o.freq;
        }

    }

    public static void main(String[] args) throws IOException {

        Huffman huff = new Huffman();

        String input = huff.readFromFile("inputText.txt");

        System.out.println("INPUT TEXT FROM FILE: inputText.txt");
        System.out.println(input + "\n");

        String encoded = huff.compress(input);
        System.out.println("HUFFMAN ENCODED: ");
        System.out.println(encoded + "\n");

        System.out.println("HUFFMAN DECODED: ");
        System.out.println(huff.decompress(encoded) + "\n");

    }
}