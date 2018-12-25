package experiment;

import java.text.Format;
import java.util.*;

public class main {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Formatter formatter = new Formatter(System.out);
        int [] address = new int[256];
        int p = 256;
        System.out.println("请输入您要使用的算法名称(lru、opt、fifo、lfu): ");
        String choose = in.next();
        init(p, address);
        printLine();
        dividePage(p, address, choose);
    }

    public static void init(int p, int [] address) {
        Formatter formatter = new Formatter(System.out);
        System.out.println("The Virtual Address Stream As Follows:");
        for(int i = 0; i < p; i++) {
            if (i < 128) {
                address[i] = 16895 + i;
            } else if (i < 192) {
                address[i] = (int) (Math.random() * 15873);
            } else {
                address[i] = (int) (Math.random() * 15873) + 15873;
            }
        }
        printArray(p, address);
    }
    public static void OPT_Print(int p,int pagesize, int [] pageno){
        System.out.println("PAGE NUMBER WITH SIZE " + pagesize + "k FOR EACH ADDRESS IS:");
        printArray2(p, pageno);
        System.out.println("vmsize=32k      pagesize=" + pagesize + "k");
        printLine();
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-25s","page assigned");
        formatter.format("%-30s","pages_in/total references");
        System.out.println();
        double rate;
        for(int i = 4; i <= 32; i += 2) {
            rate = OPT(i, p, pageno);
            formatter.format("%-25d", i);
            formatter.format("%-30f", rate);
            System.out.println();
        }
    }
    public static double OPT(int m, int p, int [] pageno) {
        int [] temp = new int[m];
        int [] index = new int[m];
        int num = 0;
        int flag1 = 0;
        int maxindex = 0;
        for(int i = 0; i < m; i++) {
            temp[i] = 0;
        }
        for(int i = 0; i < p; i++) {
//            System.out.println(i + ": " + Arrays.toString(temp) + " " + num);
            flag1 =  0;
            maxindex = 0;
            for(int j = 0; j < m; j++) {
                index[j] = p;
            }
            for(int j = 0; j < m; j++) {
                if(temp[j] == pageno[i]) {
                    flag1 = 1;
                    break;
                }
                if(temp[j] == 0) {
                    temp[j] = pageno[i];
                    num++;
                    flag1 = 1;
                    break;
                }
            }
            if(flag1 == 1) {
                continue;
            }
            else if(flag1 == 0) {
                for(int j = 0; j < m; j++) {
                    for(int k = i + 1; k < p; k++) {
                        if(pageno[k] == temp[j]) {
                            index[j] = k;
                            break;
                        }
                    }
                }
                int max = index[0];
                for(int j = 0; j < m; j++) {
                    if(index[j] == p) {
                        maxindex = j;
                        break;
                    }
                    if(index[j] > max) {
                        maxindex = j;
                        max = index[j];
                    }
                }
//                System.out.println(Arrays.toString(index));
                temp[maxindex] = pageno[i];
                num++;
            }
        }
//        System.out.println(num);
        double rate;
        rate = 1 - ((double)num)/p;
//        System.out.println(rate);
        return rate;
    }
    public static void LRU_Print(int p, int pagesize, int [] pageno){
        System.out.println("PAGE NUMBER WITH SIZE " + pagesize + "k FOR EACH ADDRESS IS:");
        printArray2(p, pageno);
        System.out.println("vmsize=32k      pagesize=" + pagesize + "k");
        printLine();
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-25s","page assigned");
        formatter.format("%-30s","pages_in/total references");
        System.out.println();
        double rate;
        for(int i = 4; i <= 32; i += 2) {
            rate = LRU(i, p, pageno);
            formatter.format("%-25d", i);
            formatter.format("%-30f", rate);
            System.out.println();
        }
    }
    public static double LRU(int m, int p, int [] pageno) {
        LinkedList linkList = new LinkedList();
        int num = 0;
        for(int i = 0;i < p; i++) {
            if(linkList.size() < m && !linkList.contains(pageno[i])) {
                linkList.addLast(pageno[i]);
                num++;
            } else if(linkList.size() == m && !linkList.contains(pageno[i])) {
                linkList.removeFirst();
                linkList.addLast(pageno[i]);
                num++;
            } else if(linkList.contains(pageno[i])) {
                linkList.remove(pageno[i]);
                linkList.addLast(pageno[i]);
            }
        }
        double rate;
        rate = 1 - ((double)num)/p;
//        System.out.println(rate);
        return rate;
    }

    public static void FIFO_Print(int p, int pagesize, int [] pageno){
        System.out.println("PAGE NUMBER WITH SIZE " + pagesize + "k FOR EACH ADDRESS IS:");
        printArray2(p, pageno);
        System.out.println("vmsize=32k      pagesize=" + pagesize + "k");
        printLine();
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-25s","page assigned");
        formatter.format("%-30s","pages_in/total references");
        System.out.println();
        double rate;
        for(int i = 4; i <= 32; i += 2) {
            rate = FIFO(i, p, pageno);
            formatter.format("%-25d", i);
            formatter.format("%-30f", rate);
            System.out.println();
        }
    }
    public static double FIFO(int m, int p, int [] pageno) {
        LinkedList linkList = new LinkedList();
        int num = 0;
        for(int i = 0;i < p; i++) {
            if(linkList.size() < m && !linkList.contains(pageno[i])) {
                linkList.addLast(pageno[i]);
                num++;
            } else if(linkList.size() == m && !linkList.contains(pageno[i])) {
                linkList.removeFirst();
                linkList.addLast(pageno[i]);
                num++;
            }
        }
        double rate;
        rate = 1 - ((double)num)/p;
//        System.out.println(rate);
        return rate;
    }

    public static void LFU_Print(int p, int pagesize, int [] pageno){
        System.out.println("PAGE NUMBER WITH SIZE " + pagesize + "k FOR EACH ADDRESS IS:");
        printArray2(p, pageno);
        System.out.println("vmsize=32k      pagesize=" + pagesize + "k");
        printLine();
        Formatter formatter = new Formatter(System.out);
        formatter.format("%-25s","page assigned");
        formatter.format("%-30s","pages_in/total references");
        System.out.println();
        double rate;
        for(int i = 4; i <= 32; i += 2) {
            rate = LFU(i, p, pageno);
            formatter.format("%-25d", i);
            formatter.format("%-30f", rate);
            System.out.println();
        }
    }
    public static double LFU(int m, int p, int [] pageno) {
        LinkedList<Node> linkList = new LinkedList<Node>();
        int num = 0;
        for(int i = 0;i < p; i++) {
//            printNodeList(linkList);
//            System.out.println("添加元素: " + pageno[i]);
            if(linkList.size() < m && !contains(linkList, pageno[i])) {
                Node node = new Node(pageno[i]);
                linkList.addLast(node);
                num++;
            } else if(linkList.size() == m && !contains(linkList, pageno[i])) {
                Node node = new Node(pageno[i]);
                linkList.removeFirst();
                linkList.addLast(node);
                num++;
            } else if(contains(linkList, pageno[i])) {
                int index = 0;
                index = find(linkList, pageno[i]);
                Node node = linkList.get(index);
                node.value++;
            }
            Collections.sort(linkList, new NodeCompare());
        }
        double rate;
        rate = 1 - ((double)num)/p;
//        System.out.println(rate);
        return rate;
    }

    public static void printNodeList(LinkedList<Node> linkedList) {
        Formatter formatter = new Formatter(System.out);
        String s = "链表:";
        formatter.format("%-8s", s);
        for(Node item: linkedList) {
            formatter.format("%-5d", item.key);
        }
        System.out.println();
        s = "次数:";
        formatter.format("%-8s", s);
        for(Node item : linkedList) {
            formatter.format("%-5d", item.value);
        }
        System.out.println();
    }

    public static boolean contains(LinkedList<Node> linkedList, int temp) {
        for(Node item : linkedList) {
            if(temp == item.key) {
                return true;
            }
        }
        return false;
    }

    public static int find(LinkedList<Node> linkedList, int temp) {
        int index = 0;
        for(Node item : linkedList) {
            if(item.key == temp) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static void printLine() {
        System.out.println("---------------------------------------------------------");
    }
    public static void printArray(int p, int [] array) {
        Formatter formatter = new Formatter(System.out);
        for(int i = 0; i < p; i++) {
            String s = "a[" + i +"]=" + array[i];
            formatter.format("%-20s", s);
            if (i % 4 == 3) {
                System.out.println();
            }
        }
    }
    public static void printArray2(int p, int [] array) {
        Formatter formatter = new Formatter(System.out);
        for(int i = 0; i < p; i++) {
            String s = "pageno[" + i +"]=" + array[i];
            formatter.format("%-20s", s);
            if (i % 4 == 3) {
                System.out.println();
            }
        }
        System.out.println();
    }
    public static void dividePage(int p, int [] address, String choose){
        int [][] pagenos = new int [8][];
        for(int pagesize = 1; pagesize <= 8; pagesize++) {
            pagenos[pagesize - 1] = address.clone();
            for(int i = 0; i < p; i++) {
                pagenos[pagesize - 1][i] = (pagenos[pagesize - 1][i] / (pagesize * 1024)) + 1;
            }
        }
        if(choose.equals("opt")) {
            System.out.println("The algorithm is : opt");
            for (int pagesize = 1; pagesize <= 8; pagesize++) {
                OPT_Print(p, pagesize, pagenos[pagesize - 1]);
            }
            System.out.println("End the result for opt");
            printLine();
        } else if(choose.equals("lru")) {
            System.out.println("the algorithm is lru");
            for (int pagesize = 1; pagesize <= 8; pagesize++) {
                LRU_Print(p, pagesize, pagenos[pagesize - 1]);
            }
            System.out.println("End the result for lru");
        } else if(choose.equals("fifo")) {
            System.out.println("the algorithm is fifo");
            for (int pagesize = 1; pagesize <= 8; pagesize++) {
                FIFO_Print(p, pagesize, pagenos[pagesize - 1]);
            }
            System.out.println("End the result for fifo");
        } else if(choose.equals("lfu")) {
            System.out.println("the algorithm is lfu");
            for (int pagesize = 1; pagesize <= 8; pagesize++) {
                LFU_Print(p, pagesize, pagenos[pagesize - 1]);
            }
            System.out.println("End the result for lfu");
        }
    }
}
