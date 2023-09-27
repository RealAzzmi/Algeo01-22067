package image;

import javax.imageio.ImageIO;

import linearalgebra.Matrix;
import bicubic.Bicubic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    BufferedImage image;

    Matrix[][] redInterpolation;
    Matrix[][] greenInterpolation;
    Matrix[][] blueInterpolation;

    public void load(String fileLocation) {
         try {
            image = ImageIO.read(new File(fileLocation));
            redInterpolation = new Matrix[image.getWidth() - 3][image.getHeight() - 3];
            greenInterpolation = new Matrix[image.getWidth() - 3][image.getHeight() - 3];
            blueInterpolation = new Matrix[image.getWidth() - 3][image.getHeight() - 3];
            int interpolationWidth = image.getWidth() - 3;
            int interpolationHeight = image.getHeight() - 3;
            for (int x = 0; x < interpolationWidth; ++x) {
                for (int y = 0; y < interpolationHeight; ++y) {
                    redInterpolation[x][y] = new Matrix(16, 1);
                    greenInterpolation[x][y] = new Matrix(16, 1);
                    blueInterpolation[x][y] = new Matrix(16, 1);

                    int xPixel = x + 1;
                    int yPixel = y + 1;

                    int color00 = image.getRGB(xPixel, yPixel);
                    int red00 = (color00 & 0xff0000) >> 16;
                    int green00 = (color00 & 0xff00) >> 8;
                    int blue00 = color00 & 0xff;

                    int color10 = image.getRGB(xPixel + 1, yPixel);
                    int red10 = (color10 & 0xff0000) >> 16;
                    int green10 = (color10 & 0xff00) >> 8;
                    int blue10 = color10 & 0xff;

                    int color01 = image.getRGB(xPixel, yPixel + 1);
                    int red01 = (color01 & 0xff0000) >> 16;
                    int green01 = (color01 & 0xff00) >> 8;
                    int blue01 = color01 & 0xff;

                    int color11 = image.getRGB(xPixel + 1, yPixel + 1);
                    int red11 = (color11 & 0xff0000) >> 16;
                    int green11 = (color11 & 0xff00) >> 8;
                    int blue11 = color11 & 0xff;

                    // 12 pixels
                    // first corner
                    int colorm10 = image.getRGB(xPixel - 1, yPixel);
                    int redm10 = (colorm10 & 0xff0000) >> 16;
                    int greenm10 = (colorm10 & 0xff00) >> 8;
                    int bluem10 = colorm10 & 0xff;

                    int colorm1m1 = image.getRGB(xPixel - 1, yPixel - 1);
                    int redm1m1 = (colorm1m1 & 0xff0000) >> 16;
                    int greenm1m1 = (colorm1m1 & 0xff00) >> 8;
                    int bluem1m1 = colorm1m1 & 0xff;

                    int color0m1 = image.getRGB(xPixel, yPixel - 1);
                    int red0m1 = (color0m1 & 0xff0000) >> 16;
                    int green0m1 = (color0m1 & 0xff00) >> 8;
                    int blue0m1 = color0m1 & 0xff;

                    // second corner
                    int color1m1 = image.getRGB(xPixel + 1, yPixel - 1);
                    int red1m1 = (color1m1 & 0xff0000) >> 16;
                    int green1m1 = (color1m1 & 0xff00) >> 8;
                    int blue1m1 = color1m1 & 0xff;

                    int color2m1 = image.getRGB(xPixel + 2, yPixel - 1);
                    int red2m1 = (color2m1 & 0xff0000) >> 16;
                    int green2m1 = (color2m1 & 0xff00) >> 8;
                    int blue2m1 = color2m1 & 0xff;

                    int color20 = image.getRGB(xPixel + 2, yPixel);
                    int red20 = (color20 & 0xff0000) >> 16;
                    int green20 = (color20 & 0xff00) >> 8;
                    int blue20 = color20 & 0xff;

                    // third corner

                    int color21 = image.getRGB(xPixel + 2, yPixel + 1);
                    int red21 = (color21 & 0xff0000) >> 16;
                    int green21 = (color21 & 0xff00) >> 8;
                    int blue21 = color21 & 0xff;

                    int color22 = image.getRGB(xPixel + 2, yPixel + 2);
                    int red22 = (color22 & 0xff0000) >> 16;
                    int green22 = (color22 & 0xff00) >> 8;
                    int blue22 = color22 & 0xff;

                    int color12 = image.getRGB(xPixel + 1, yPixel + 2);
                    int red12 = (color12 & 0xff0000) >> 16;
                    int green12 = (color12 & 0xff00) >> 8;
                    int blue12 = color12 & 0xff;
                    // fourth corner

                    int color02 = image.getRGB(xPixel, yPixel + 2);
                    int red02 = (color02 & 0xff0000) >> 16;
                    int green02 = (color02 & 0xff00) >> 8;
                    int blue02 = color02 & 0xff;

                    int colorm12 = image.getRGB(xPixel - 1, yPixel + 2);
                    int redm12 = (colorm12 & 0xff0000) >> 16;
                    int greenm12 = (colorm12 & 0xff00) >> 8;
                    int bluem12 = colorm12 & 0xff;

                    int colorm11 = image.getRGB(xPixel - 1, yPixel + 1);
                    int redm11 = (colorm11 & 0xff0000) >> 16;
                    int greenm11 = (colorm11 & 0xff00) >> 8;
                    int bluem11 = colorm11 & 0xff;

                    // red
                    
                    redInterpolation[x][y].matrix[0][0] = red00;
                    redInterpolation[x][y].matrix[1][0] = red10;
                    redInterpolation[x][y].matrix[2][0] = red01;
                    redInterpolation[x][y].matrix[3][0] = red11;

                    redInterpolation[x][y].matrix[4][0] = (Double.valueOf(red10) - Double.valueOf(redm10)) / 2;
                    redInterpolation[x][y].matrix[5][0] = (Double.valueOf(red20) - Double.valueOf(red00)) / 2;
                    redInterpolation[x][y].matrix[6][0] = (Double.valueOf(red11) - Double.valueOf(redm11)) / 2;
                    redInterpolation[x][y].matrix[7][0] = (Double.valueOf(red21) - Double.valueOf(red01)) / 2;

                    redInterpolation[x][y].matrix[8][0] = (Double.valueOf(red01) - Double.valueOf(red0m1)) / 2;
                    redInterpolation[x][y].matrix[9][0] = (Double.valueOf(red11) - Double.valueOf(red1m1)) / 2;
                    redInterpolation[x][y].matrix[10][0] = (Double.valueOf(red02) - Double.valueOf(red00)) / 2;
                    redInterpolation[x][y].matrix[11][0] = (Double.valueOf(red12) - Double.valueOf(red10)) / 2;
                    
                    redInterpolation[x][y].matrix[12][0] = (Double.valueOf(red11) - Double.valueOf(redm10) - Double.valueOf(red0m1) - Double.valueOf(red00)) / 4;
                    redInterpolation[x][y].matrix[13][0] = (Double.valueOf(red21) - Double.valueOf(red00) - Double.valueOf(red1m1) - Double.valueOf(red10)) / 4;
                    redInterpolation[x][y].matrix[14][0] = (Double.valueOf(red12) - Double.valueOf(redm11) - Double.valueOf(red00) - Double.valueOf(red01)) / 4;
                    redInterpolation[x][y].matrix[15][0] = (Double.valueOf(red22) - Double.valueOf(red01) - Double.valueOf(red10) - Double.valueOf(red11)) / 4;
                
                    // green
                    greenInterpolation[x][y].matrix[0][0] = green00;
                    greenInterpolation[x][y].matrix[1][0] = green10;
                    greenInterpolation[x][y].matrix[2][0] = green01;
                    greenInterpolation[x][y].matrix[3][0] = green11;

                    greenInterpolation[x][y].matrix[4][0] = (Double.valueOf(green10) - Double.valueOf(greenm10)) / 2;
                    greenInterpolation[x][y].matrix[5][0] = (Double.valueOf(green20) - Double.valueOf(green00)) / 2;
                    greenInterpolation[x][y].matrix[6][0] = (Double.valueOf(green11) - Double.valueOf(greenm11)) / 2;
                    greenInterpolation[x][y].matrix[7][0] = (Double.valueOf(green21) - Double.valueOf(green01)) / 2;

                    greenInterpolation[x][y].matrix[8][0] = (Double.valueOf(green01) - Double.valueOf(green0m1)) / 2;
                    greenInterpolation[x][y].matrix[9][0] = (Double.valueOf(green11) - Double.valueOf(green1m1)) / 2;
                    greenInterpolation[x][y].matrix[10][0] = (Double.valueOf(green02) - Double.valueOf(green00)) / 2;
                    greenInterpolation[x][y].matrix[11][0] = (Double.valueOf(green12) - Double.valueOf(green10)) / 2;
                    
                    greenInterpolation[x][y].matrix[12][0] = (Double.valueOf(green11) - Double.valueOf(greenm10) - Double.valueOf(green0m1) - Double.valueOf(green00)) / 4;
                    greenInterpolation[x][y].matrix[13][0] = (Double.valueOf(green21) - Double.valueOf(green00) - Double.valueOf(green1m1) - Double.valueOf(green10)) / 4;
                    greenInterpolation[x][y].matrix[14][0] = (Double.valueOf(green12) - Double.valueOf(greenm11) - Double.valueOf(green00) - Double.valueOf(green01)) / 4;
                    greenInterpolation[x][y].matrix[15][0] = (Double.valueOf(green22) - Double.valueOf(green01) - Double.valueOf(green10) - Double.valueOf(green11)) / 4;

                    // blue
                    blueInterpolation[x][y].matrix[0][0] = blue00;
                    blueInterpolation[x][y].matrix[1][0] = blue10;
                    blueInterpolation[x][y].matrix[2][0] = blue01;
                    blueInterpolation[x][y].matrix[3][0] = blue11;

                    blueInterpolation[x][y].matrix[4][0] = (Double.valueOf(blue10) - Double.valueOf(bluem10)) / 2;
                    blueInterpolation[x][y].matrix[5][0] = (Double.valueOf(blue20) - Double.valueOf(blue00)) / 2;
                    blueInterpolation[x][y].matrix[6][0] = (Double.valueOf(blue11) - Double.valueOf(bluem11)) / 2;
                    blueInterpolation[x][y].matrix[7][0] = (Double.valueOf(blue21) - Double.valueOf(blue01)) / 2;

                    blueInterpolation[x][y].matrix[8][0] = (Double.valueOf(blue01) - Double.valueOf(blue0m1)) / 2;
                    blueInterpolation[x][y].matrix[9][0] = (Double.valueOf(blue11) - Double.valueOf(blue1m1)) / 2;
                    blueInterpolation[x][y].matrix[10][0] = (Double.valueOf(blue02) - Double.valueOf(blue00)) / 2;
                    blueInterpolation[x][y].matrix[11][0] = (Double.valueOf(blue12) - Double.valueOf(blue10)) / 2;
                    
                    blueInterpolation[x][y].matrix[12][0] = (Double.valueOf(blue11) - Double.valueOf(bluem10) - Double.valueOf(blue0m1) - Double.valueOf(blue00)) / 4;
                    blueInterpolation[x][y].matrix[13][0] = (Double.valueOf(blue21) - Double.valueOf(blue00) - Double.valueOf(blue1m1) - Double.valueOf(blue10)) / 4;
                    blueInterpolation[x][y].matrix[14][0] = (Double.valueOf(blue12) - Double.valueOf(bluem11) - Double.valueOf(blue00) - Double.valueOf(blue01)) / 4;
                    blueInterpolation[x][y].matrix[15][0] = (Double.valueOf(blue22) - Double.valueOf(blue01) - Double.valueOf(blue10) - Double.valueOf(blue11)) / 4;
                }
            }
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void resize(double factor, String fileLocation) {
        try {
            int width = (int)Math.round(image.getWidth() * factor);
            int height = (int)Math.round(image.getHeight() * factor);

            BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    double realX = ((double)x / width) * (image.getWidth() - 3);
                    double realY = ((double)y / height) * (image.getHeight() - 3);

                    int alpha = 255;
                    double red = Bicubic.approximate(redInterpolation[(int)realX][(int)realY], (realX % 1), (realY % 1));
                    double green = Bicubic.approximate(greenInterpolation[(int)realX][(int)realY], (realX % 1), (realY % 1));
                    double blue = Bicubic.approximate(blueInterpolation[(int)realX][(int)realY], (realX % 1), (realY % 1));
                    

                    int sentRed = (int)Math.round(red);
                    int sentGreen = (int)Math.round(green);
                    int sentBlue = (int)Math.round(blue);
                    
                    if (sentRed < 0) sentRed = 0;
                    if (sentGreen < 0) sentGreen = 0;
                    if (sentBlue < 0) sentBlue = 0;

                    if (sentRed == 256) --sentRed;
                    if (sentGreen == 256) --sentGreen;
                    if (sentBlue == 256) --sentBlue;


                    int argb = (alpha << 24) + (sentRed << 16) + (sentGreen << 8) + sentBlue;
                    output.setRGB(x, y, argb);

                }
            }
            ImageIO.write(output, "png", new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
