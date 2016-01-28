public class ImageProcessor {
    private Pic pic;
    public ImageProcessor(Pic pt) {
        this.pic = pt;
    }
    public Pic greyscale() {
        Pixel[][] px;
        Pic pt = pic.deepCopy();
        px = pt.getPixels();
        for (int row = 0; row < px.length; row++) {
            for (int col = 0; col < px[row].length; col++) {
                int a = px[row][col].getAlpha();
                int r = px[row][col].getRed();
                int g = px[row][col].getGreen();
                int b = px[row][col].getBlue();
                int ave = (r + g + b) / 3;
                px[row][col].setRed(ave);
                px[row][col].setBlue(ave);
                px[row][col].setGreen(ave);
            }
        }
        return pt;
    }
    public Pic invert() {
        Pixel[][] px;
        Pic pt = pic.deepCopy();
        px = pt.getPixels();
        for (int row = 0; row < px.length; row++) {
            for (int col = 0; col < px[row].length; col++) {
                int a = px[row][col].getAlpha();
                int r = px[row][col].getRed();
                int g = px[row][col].getGreen();
                int b = px[row][col].getBlue();
                px[row][col].setRed(255 - r);
                px[row][col].setBlue(255 - b);
                px[row][col].setGreen(255 - g);
            }
        }
        return pt;
    }
    public Pic nored() {
        Pixel[][] px;
        Pic pt = pic.deepCopy();
        px = pt.getPixels();

        for (int row = 0; row < px.length; row++) {
            for (int col = 0; col < px[row].length; col++) {
                px[row][col].setRed(0);
            }
        }
        return pt;
    }
    public Pic blackAndWhite() {
        Pixel[][] px;
        Pic pt = pic.deepCopy();
        px = pt.getPixels();
        for (int row = 0; row < px.length; row++) {
            for (int col = 0; col < px[row].length; col++) {
                int a = px[row][col].getAlpha();
                int r = px[row][col].getRed();
                int g = px[row][col].getGreen();
                int b = px[row][col].getBlue();
                int ave = (r + g + b) / 3;
                if (ave < 256 && ave > -1) {
                    if (ave > 127) {
                        px[row][col].setRed(255);
                        px[row][col].setBlue(255);
                        px[row][col].setGreen(255);
                    } else {
                        px[row][col].setRed(0);
                        px[row][col].setBlue(0);
                        px[row][col].setGreen(0);
                    }
                }
            }
        }
        return pt;
    }
    public Pic maximize() {
        Pixel[][] px;
        Pic pt = pic.deepCopy();
        px = pt.getPixels();
        for (int row = 0; row < px.length; row++) {
            for (int col = 0; col < px[row].length; col++) {
                int r = px[row][col].getRed();
                int g = px[row][col].getGreen();
                int b = px[row][col].getBlue();
                if (r > g && r > b) {
                    px[row][col].setGreen(0);
                    px[row][col].setBlue(0);
                } else if (g > r && g > b) {
                    px[row][col].setRed(0);
                    px[row][col].setBlue(0);
                } else if (b > r && b > g) {
                    px[row][col].setRed(0);
                    px[row][col].setGreen(0);
                } else if (r == b && r > g) {
                    px[row][col].setGreen(0);
                } else if (r == g && r > b) {
                    px[row][col].setBlue(0);
                } else if (g == b && g > r) {
                    px[row][col].setRed(0);
                }
            }
        }
        return pt;
    }
    public Pic overlay(Pic cpt) {
        Pixel[][] px;
        Pixel[][] cpx;
        Pic pt = pic.deepCopy();
        px = pt.getPixels();
        cpx = cpt.getPixels();
        for (int row = 0; row < cpx.length; row++) {
            for (int col = 0; col < cpx[row].length; col++) {
                int r = px[row][col].getRed();
                int g = px[row][col].getGreen();
                int b = px[row][col].getBlue();
                int cr = cpx[row][col].getRed();
                int cg = cpx[row][col].getGreen();
                int cb = cpx[row][col].getBlue();
                int rave = (r + cr) / 2;
                int gave = (g + cg) / 2;
                int bave = (b + cb) / 2;

                px[row][col].setRed(rave);
                px[row][col].setBlue(bave);
                px[row][col].setGreen(gave);
            }
        }
        return pt;
    }
    public static void main(String[] args) {
        Pic pic1 = new Pic(args[0]);
        ImageProcessor ip = new ImageProcessor(pic1);
        ip.greyscale().show();
        ip.invert().show();
        ip.nored().show();
        ip.blackAndWhite().show();
        ip.maximize().show();
        Pic cpic;
        if (args.length == 2) {
            cpic = new Pic(args[1]);
            ip.overlay(cpic).show();
        }
    }
}