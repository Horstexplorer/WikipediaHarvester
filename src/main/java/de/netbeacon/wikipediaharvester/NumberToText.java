package de.netbeacon.wikipediaharvester;

//https://www.sonntag.cc/teaching/JAVA-Kurs/Methoden/html/NumberToText.java.html

class NumberToText {

    private String intToText10(int x) {
        int y = x / 10;
        switch (y) {
            case 1:
                return "zehn"; //10
            case 2:
                return "zwanzig"; //20
            case 3:
                return "dreißig"; //30
            case 4:
                return "vierzig"; //40
            case 5:
                return "fünfzig"; //50
            case 6:
                return "sechzig"; //60
            case 7:
                return "siebzig"; //70
            case 8:
                return "achtzig"; //80
            case 9:
                return "neunzig"; //90
            default:
                return "FEHLER"; //error
        }
    }

    private String intToText100(int x, int digits) {
        int y = x % 100;

        if (y == 1) {
            switch (digits) {
                case 0:
                    return "eins"; //1 like in 1
                case 1:
                case 2:
                case 3:
                    return "ein"; //1 like in 100
                case 6:
                case 9:
                case 12:
                case 15:

                    if (x == 1) {
                        return "eine";
                    } else {
                        return "ein";
                    }
                default:
                    return "FEHLER";
            }
        }

        if ((y >= 2) && (y <= 19)) {
            switch (y) {
                case 2:
                    return "zwei"; //2
                case 3:
                    return "drei"; //3
                case 4:
                    return "vier"; //4
                case 5:
                    return "fünf"; //5
                case 6:
                    return "sechs"; //6
                case 7:
                    return "sieben"; //7
                case 8:
                    return "acht"; //8
                case 9:
                    return "neun"; //9
                case 10:
                    return "zehn"; //10
                case 11:
                    return "elf"; //11
                case 12:
                    return "zwölf"; //12
                case 13:
                    return "dreizehn"; //13
                case 14:
                    return "vierzehn"; //14
                case 15:
                    return "fünfzehn"; //15
                case 16:
                    return "sechzehn"; //16
                case 17:
                    return "siebzehn"; //17
                case 18:
                    return "achtzehn"; //16
                case 19:
                    return "neunzehn"; //18
                default:
                    return "FEHLER"; //error
            }
        }

        if ((y >= 20) && (y <= 99)) {
            if (y % 10 == 0) {
                return intToText10(y);
            } else {
                return intToText100(y % 10, 1) + "und" + intToText10(y); //and
            }
        }
        return "";
    }

    private String intToText1000(int x, int digits) {
        if (x / 100 == 0) {
            return intToText100(x, digits);
        } else {
            return intToText100(x / 100, 2) + "hundert" + intToText100(x, digits); //100
        }
    }

    private String intToTextDigits(int digits, boolean mz) {
        if (mz) {
            switch (digits) {
                case 0:
                    return "";
                case 3:
                    return "tausend"; //1000
                case 6:
                    return " Millionen "; //1000000
                case 9:
                    return " Milliarden "; //1000000000
                case 12:
                    return " Billionen "; //1000000000000
                case 15:
                    return " Billiarden "; //1000000000000000
                default:
                    return "";
            }
        } else {
            switch (digits) {
                case 0:
                    return "";
                case 3:
                    return "tausend"; //1000
                case 6:
                    return " Million "; //1000000
                case 9:
                    return " Milliarde "; //1000000000
                case 12:
                    return " Billion "; //1000000000000
                case 15:
                    return " Billiarde "; //1000000000000000
                default:
                    return "";
            }
        }
    }

    String intToText(int x) {
        int digits;
        String result;

        if (x == 0) {
            return "null";
        }

        digits = 0;
        result = "";
        while (x > 0) {
            result = (x % 1000 > 0 ? (intToText1000(x % 1000, digits) + intToTextDigits(digits, x % 1000 > 1)) : "") + result;
            x /= 1000;
            digits += 3;
        }
        return result;
    }
}