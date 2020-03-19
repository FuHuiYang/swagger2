package com.yang.test;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.test
 * @Description: TODO
 * @date Date : 2019年09月12日 16:34
 */
public class B {
    private Boolean flag = true;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    public static void main(String[] args) {
        System.out.println(cleanBlank("189 5895 2	56"));
        System.out.println(cleanBlank("15　822  69 66 "));
        System.out.println(cleanBlank("189 5895 "
                + "			256"));
        System.out.println("-------------------------------------------------");
        //不能去除示例　输入法改成中文全角
        System.out.println("1582269　66　　　　1".replaceAll("\\s*", ""));
        //不能去除示例　输入法改成中文全角　无法去除TAB建
        System.out.println("1　　　582　26		9661 ".replaceAll("　", ""));
    }

    /**
     * 清理空白字符
     *
     * @param str 被清理的字符串
     * @return 清理后的字符串
     */
    public static String cleanBlank(CharSequence str) {
        if (str == null) {
            return null;
        }

        int len = str.length();
        final StringBuilder sb = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (false == isBlankChar(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == '\ufeff' || c == '\u202a';
    }
}
