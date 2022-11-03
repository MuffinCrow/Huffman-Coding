package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * 
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) {
        fileName = f;
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by
     * frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        char cur;
        CharFreq freq;
        sortedCharFreqList = new ArrayList<CharFreq>();
        int counter = 0;
        boolean check = false;

        while (StdIn.hasNextChar()) {
            cur = StdIn.readChar();
            counter++;
            freq = new CharFreq(cur, 1);
            check = false;
            int conv;
            conv = (int) cur;
            for (int i = 0; i < sortedCharFreqList.size(); i++) {
                if (((int) sortedCharFreqList.get(i).getCharacter()) == conv) {
                    sortedCharFreqList.get(i).setProbOcc(sortedCharFreqList.get(i).getProbOcc() + 1);
                    check = true;
                    break;
                }
            }
            if (sortedCharFreqList.indexOf(freq) == -1 && check == false) {
                sortedCharFreqList.add(freq);
            }
        }

        Collections.sort(sortedCharFreqList);

        for (int i = 0; i < sortedCharFreqList.size(); ++i) {
            sortedCharFreqList.get(i).setProbOcc(sortedCharFreqList.get(i).getProbOcc() / counter);
        }

        if (sortedCharFreqList.size() == 1) {
            int temp = (int) sortedCharFreqList.get(0).getCharacter();
            temp++;
            sortedCharFreqList.add(new CharFreq((char) temp, 0));

            Collections.sort(sortedCharFreqList);
        }
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */

    private ArrayList<TreeNode> eh(ArrayList<TreeNode> bitch) {
        int cur;

        for (int i = 0; i < bitch.size() - 1; ++i) {
            cur = i;
            for (int j = i + 1; j < bitch.size(); ++j) {
                if (bitch.get(j).getData().getProbOcc() < bitch.get(i).getData().getProbOcc()) {
                    cur = j;
                }
            }
            TreeNode fucker = bitch.get(cur);
            bitch.set(cur, bitch.get(i));
            bitch.set(i, fucker);
        }
        return bitch;
    }

    private ArrayList<TreeNode> killMe(TreeNode noose, ArrayList<TreeNode> target) {
        if (sortedCharFreqList.indexOf(noose.getData()) >= 0) {
            sortedCharFreqList.remove(sortedCharFreqList.indexOf(noose.getData()));
        } else if (target.indexOf(noose) >= 0) {
            target.remove(target.indexOf(noose));
        }
        return target;
    }

    public void makeTree() {
        ArrayList<TreeNode> target = new ArrayList<>();
        TreeNode fir;
        TreeNode sec;

        while (sortedCharFreqList.size() > 0) {
            /*
             * if (target.size() > 2) {
             * while (target.size() > 1) {
             * fir = target.remove(0);
             * sec = target.remove(0);
             * 
             * TreeNode anal = new TreeNode(
             * new CharFreq(null, fir.getData().getProbOcc() + sec.getData().getProbOcc()),
             * fir, sec);
             * 
             * target.add(anal);
             * 
             * target = eh(target);
             * }
             * } else {
             */
            if (target.size() > 0) {
                if (target.size() > 1 && sortedCharFreqList.size() <= 0) {
                    target = eh(target);
                    break;
                }
                // if (target.size() > 1 && sortedCharFreqList.size() <= 0)
                int sortC = 0, tarC = 0;
                fir = new TreeNode(sortedCharFreqList.get(sortC), null, null);
                sortC++;
                if (sortedCharFreqList.size() > 1) {
                    sec = new TreeNode(sortedCharFreqList.get(sortC), null, null);
                    sortC++;
                } else {
                    sec = target.get(tarC);
                    tarC++;
                }

                while (sortC < sortedCharFreqList.size()) {
                    if (sortedCharFreqList.get(sortC).getProbOcc() < sec.getData().getProbOcc()) {
                        if (sortedCharFreqList.get(sortC).getProbOcc() < fir.getData().getProbOcc()) {
                            sec = fir;
                            fir = new TreeNode(sortedCharFreqList.get(sortC), null, null);
                        } else {
                            sec = new TreeNode(sortedCharFreqList.get(sortC), null, null);
                        }
                    }
                    sortC++;
                }

                while (tarC < target.size()) {
                    if (target.get(tarC).getData().getProbOcc() < sec.getData().getProbOcc()) {
                        if (target.get(tarC).getData().getProbOcc() < fir.getData().getProbOcc()) {
                            sec = fir;
                            fir = target.get(tarC);
                        } else {
                            sec = target.get(tarC);
                        }
                    }
                    tarC++;
                }
            } else {
                fir = new TreeNode(sortedCharFreqList.get(0), null, null);
                if (sortedCharFreqList.size() > 1) {
                    sec = new TreeNode(sortedCharFreqList.get(1), null, null);
                } else {
                    huffmanRoot = fir;
                    sortedCharFreqList.clear();
                    break;
                }

            }
            target = killMe(fir, target);
            target = killMe(sec, target);
            target.add(new TreeNode(new CharFreq(null, fir.getData().getProbOcc() + sec.getData().getProbOcc()),
                    fir, sec));
            if (target.size() > 1) {
                target = eh(target);
            }
            // }
            /*
             * firSor = false;
             * secSor = false;
             * System.out.println(sortedCharFreqList.get(0).getProbOcc());
             * fir = new TreeNode(sortedCharFreqList.get(0), null, null);
             * 
             * if (sortedCharFreqList.size() > 1) {
             * sec = new TreeNode(sortedCharFreqList.get(1), null, null);
             * 
             * if (target.size() > 0) {
             * if (target.size() > 1) {
             * if (sec.getData().getProbOcc() >= target.get(0).getData().getProbOcc()) {
             * if (fir.getData().getProbOcc() >= target.get(0).getData().getProbOcc()) {
             * sec = fir;
             * fir = target.remove(0);
             * 
             * if (sec.getData().getProbOcc() >= target.get(1).getData().getProbOcc()) {
             * sec = target.remove(0);
             * }
             * } else {
             * sec = target.remove(0);
             * }
             * }
             * } else {
             * if (sec.getData().getProbOcc() >= target.get(0).getData().getProbOcc()) {
             * if (fir.getData().getProbOcc() > target.get(0).getData().getProbOcc()) {
             * sec = fir;
             * fir = target.remove(0);
             * } else {
             * sec = target.remove(0);
             * }
             * }
             * }
             * }
             * if (sortedCharFreqList.indexOf(sec.getData()) >= 0) {
             * sortedCharFreqList.remove(sortedCharFreqList.indexOf(sec.getData()));
             * }
             * } else {
             * 
             * if (target.size() > 1) {
             * if (fir.getData().getProbOcc() >= target.get(1).getData().getProbOcc()) {
             * fir = target.remove(0);
             * sec = target.remove(0);
             * } else {
             * if (fir.getData().getProbOcc() >= target.get(0).getData().getProbOcc()) {
             * sec = fir;
             * fir = target.remove(0);
             * } else {
             * sec = target.remove(0);
             * }
             * }
             * } else {
             * if (fir.getData().getProbOcc() >= target.get(0).getData().getProbOcc()) {
             * sec = fir;
             * fir = target.remove(0);
             * } else {
             * sec = target.remove(0);
             * }
             * }
             * 
             * }
             * if (sortedCharFreqList.indexOf(fir.getData()) >= 0) {
             * sortedCharFreqList.remove(sortedCharFreqList.indexOf(fir.getData()));
             * }
             * if (sortedCharFreqList.indexOf(sec.getData()) >= 0) {
             * sortedCharFreqList.remove(sortedCharFreqList.indexOf(sec.getData()));
             * }
             * 
             * TreeNode anal = new TreeNode(new CharFreq(null, fir.getData().getProbOcc() +
             * sec.getData().getProbOcc()),
             * fir, sec);
             * 
             * if (target.size() > 0) {
             * if (target.get(0).getData().getProbOcc() < anal.getData().getProbOcc()) {
             * target.add(anal);
             * } else {
             * target.add(target.get(0));
             * target.set(0, anal);
             * }
             * } else {
             * target.add(anal);
             * }
             */
        }
        while (target.size() > 1) {
            fir = target.remove(0);
            sec = target.remove(0);

            TreeNode anal = new TreeNode(
                    new CharFreq(null, fir.getData().getProbOcc() + sec.getData().getProbOcc()), fir, sec);

            target.add(anal);

            target = eh(target);
        }

        if (target.size() > 1) {
            huffmanRoot = new TreeNode(
                    new CharFreq(null, target.get(0).getData().getProbOcc() + target.get(1).getData().getProbOcc()),
                    target.get(0), target.get(1));

        } else if (huffmanRoot == null) {
            huffmanRoot = target.get(0);
        }
    }

    /*
     * private TreeNode fuckThis(TreeNode fuck, TreeNode you) {
     * if (fuck.getLeft() != null) {
     * fuckThis(fuck.getLeft(), you);
     * }
     * if (fuck.getRight() != null) {
     * fuckThis(fuck.getRight(), you);
     * }
     * }
     */

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding.
     * Characters not
     * present in the huffman coding tree should have their spots in the array left
     * null.
     * Set encodings to this array.
     */
    public void makeEncodings() {

        /* Your code goes here */
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString
     * method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);

        /* Your code goes here */
    }

    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename  The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding - 1; i++)
            pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1')
                currentByte += 1 << (7 - byteIndex);
            byteIndex++;

            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }

        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString
     * method
     * to convert the file into a bit string, then decodes the bit string using the
     * tree, and writes it to a decoded file.
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

        /* Your code goes here */
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";

        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();

            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString +
                        String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1')
                    return bitString.substring(i + 1);
            }

            return bitString.substring(8);
        } catch (Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver.
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() {
        return fileName;
    }

    public ArrayList<CharFreq> getSortedCharFreqList() {
        return sortedCharFreqList;
    }

    public TreeNode getHuffmanRoot() {
        return huffmanRoot;
    }

    public String[] getEncodings() {
        return encodings;
    }
}
