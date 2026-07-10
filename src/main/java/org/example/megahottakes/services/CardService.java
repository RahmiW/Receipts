package org.example.megahottakes.services;

import org.example.megahottakes.entities.HotTake;
import org.example.megahottakes.entities.Verdict;
import org.example.megahottakes.repositories.HotTakeRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

@Service
public class CardService {
    private static final int CARD_SIZE = 1080;
    private static final Color BACKGROUND = new Color(18, 18, 18);
    private static final Color CORRECT_COLOR = new Color(46, 204, 113);
    private static final Color INCORRECT_COLOR = new Color(231, 76, 60);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color MUTED_COLOR = new Color(160, 160, 160);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d, yyyy");

    private final HotTakeRepository hotTakeRepository;

    public CardService(HotTakeRepository hotTakeRepository) {
        this.hotTakeRepository = hotTakeRepository;
    }

    public byte[] generateCard(Long hotTakeId) {
        HotTake hotTake = hotTakeRepository.findById(hotTakeId)
                .orElseThrow(() -> new IllegalArgumentException("The HotTake was not found"));
        if (hotTake.getVerdict() == Verdict.PENDING) {
            throw new IllegalArgumentException("Cannot generate a card for a take that hasn't been resolved yet");
        }

        BufferedImage image = new BufferedImage(CARD_SIZE, CARD_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(BACKGROUND);
        g.fillRect(0, 0, CARD_SIZE, CARD_SIZE);

        boolean isCorrect = hotTake.getVerdict() == Verdict.CORRECT;
        Color verdictColor = isCorrect ? CORRECT_COLOR : INCORRECT_COLOR;
        String verdictLabel = isCorrect ? "✅ CALLED IT" : "❌ WHIFFED";

        g.setColor(verdictColor);
        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        g.drawString(verdictLabel, 60, 120);

        g.setColor(TEXT_COLOR);
        g.setFont(new Font("SansSerif", Font.BOLD, 56));
        List<String> lines = wrapText(hotTake.getContent(), g.getFontMetrics(), CARD_SIZE - 120);
        int y = 260;
        for (String line : lines) {
            g.drawString(line, 60, y);
            y += 70;
        }

        g.setColor(MUTED_COLOR);
        g.setFont(new Font("SansSerif", Font.PLAIN, 36));
        String handle = "@" + hotTake.getAuthor().getUserName();
        g.drawString(handle, 60, CARD_SIZE - 200);

        String posted = "Posted " + hotTake.getCreationDate().format(DATE_FORMAT);
        g.drawString(posted, 60, CARD_SIZE - 150);

        int correctCount = hotTakeRepository.countByAuthorIdAndVerdict(hotTake.getAuthor().getId(), Verdict.CORRECT);
        int incorrectCount = hotTakeRepository.countByAuthorIdAndVerdict(hotTake.getAuthor().getId(), Verdict.INCORRECT);
        int resolvedCount = correctCount + incorrectCount;
        int hitRatePercent = resolvedCount == 0 ? 0 : Math.round(100f * correctCount / resolvedCount);
        g.drawString("Hit rate: " + hitRatePercent + "%", 60, CARD_SIZE - 100);

        g.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return out.toByteArray();
    }

    private List<String> wrapText(String text, FontMetrics metrics, int maxWidth) {
        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (String word : text.split("\\s+")) {
            String candidate = current.isEmpty() ? word : current + " " + word;
            if (metrics.stringWidth(candidate) > maxWidth && !current.isEmpty()) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } else {
                current = new StringBuilder(candidate);
            }
        }
        if (!current.isEmpty()) {
            lines.add(current.toString());
        }
        return lines;
    }
}
