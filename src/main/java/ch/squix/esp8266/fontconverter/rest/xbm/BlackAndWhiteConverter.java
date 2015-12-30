package ch.squix.esp8266.fontconverter.rest.xbm;

// from :
// http://stackoverflow.com/questions/5940188/how-to-convert-a-24-bit-png-to-3-bit-png-using-floyd-steinberg-dithering

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class BlackAndWhiteConverter {

    public static class C3 {

        int r, g, b;

        public C3(int c) {
            Color color = new Color(c);
            r = color.getRed();
            g = color.getGreen();
            b = color.getBlue();
        }

        public C3(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public C3 add(C3 o) {
            return new C3(r + o.r, g + o.g, b + o.b);
        }

        public int clamp(int c) {
            return Math.max(0, Math.min(255, c));
        }

        public int diff(C3 o) {
            int Rdiff = o.r - r;
            int Gdiff = o.g - g;
            int Bdiff = o.b - b;
            int distanceSquared = Rdiff * Rdiff + Gdiff * Gdiff + Bdiff * Bdiff;
            return distanceSquared;
        }

        public C3 mul(double d) {
            return new C3((int) (d * r), (int) (d * g), (int) (d * b));
        }

        public C3 sub(C3 o) {
            return new C3(r - o.r, g - o.g, b - o.b);
        }

        public Color toColor() {
            return new Color(clamp(r), clamp(g), clamp(b));
        }

        public int toRGB() {
            return toColor().getRGB();
        }
    }

    private static C3 findClosestPaletteColor(C3 c, C3[] palette) {
        C3 closest = palette[0];

        for (C3 n : palette) {
            if (n.diff(c) < closest.diff(c)) {
                closest = n;
            }
        }

        return closest;
    }

    public static BufferedImage thresholdConverter(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int grayValue = (color.getBlue() + color.getRed() + color.getGreen()) / 3;
                if (grayValue > 127) {
                    color = Color.WHITE;
                } else {
                    color = Color.BLACK;
                }
                newImage.setRGB(x, y, color.getRGB());
            }
        }
        return newImage;
    }

    public static BufferedImage floydSteinbergDithering(BufferedImage img, C3 palette[]) {


        int w = img.getWidth();
        int h = img.getHeight();

        C3[][] d = new C3[h][w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                d[y][x] = new C3(img.getRGB(x, y));
            }
        }

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {

                C3 oldColor = d[y][x];
                C3 newColor = findClosestPaletteColor(oldColor, palette);
                img.setRGB(x, y, newColor.toColor().getRGB());

                C3 err = oldColor.sub(newColor);

                if (x + 1 < w) {
                    d[y][x + 1] = d[y][x + 1].add(err.mul(7. / 16));
                }

                if (x - 1 >= 0 && y + 1 < h) {
                    d[y + 1][x - 1] = d[y + 1][x - 1].add(err.mul(3. / 16));
                }

                if (y + 1 < h) {
                    d[y + 1][x] = d[y + 1][x].add(err.mul(5. / 16));
                }

                if (x + 1 < w && y + 1 < h) {
                    d[y + 1][x + 1] = d[y + 1][x + 1].add(err.mul(1. / 16));
                }
            }
        }

        return img;
    }

    public static void main(String[] args) throws IOException {
        final BufferedImage normal = ImageIO.read(
                new URL("https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png")).getSubimage(
                100, 100, 300, 300);

        C3[] palette = new C3[]{new C3(0, 0, 0), // black
                new C3(255, 255, 255) // white
        };

        final BufferedImage dithered = floydSteinbergDithering(
                ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/en/2/24/Lenna.png")),
                palette).getSubimage(100, 100, 300, 300);

        JFrame frame = new JFrame("Test");
        frame.setLayout(new GridLayout(1, 2));

        frame.add(new JComponent() {

            private static final long serialVersionUID = 2963702769416707676L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(normal, 0, 0, this);
            }
        });

        frame.add(new JComponent() {

            private static final long serialVersionUID = -6919658458441878769L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(dithered, 0, 0, this);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 350);
        frame.setVisible(true);
    }
}
