package touristPackage;

import java.io.Serializable;

/**
 * Represents a rating given by a Tourist for a TourGuide.
 * Association  : links Tourist → TourGuide (neither owns the other)
 * Serializable : can be persisted as part of a larger data structure
 * Encapsulation: all fields private, read-only after construction
 */
public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;

    private String    ratingID;
    private Tourist   tourist;
    private TourGuide guide;
    private int       score;
    private String    feedback;

    // ── Constructor ───────────────────────────────────────────────────────────
    /**
     * @throws IllegalArgumentException if score is not between 1 and 5
     */
    public Rating(String ratingID, Tourist tourist, TourGuide guide, int score, String feedback) {
        if (score < 1 || score > 5)
            throw new IllegalArgumentException("Score must be between 1 and 5.");
        this.ratingID = ratingID;
        this.tourist  = tourist;
        this.guide    = guide;
        this.score    = score;
        this.feedback = feedback;
    }

    // ── Getters (immutable after construction) ────────────────────────────────
    public String    getRatingID() { return ratingID; }
    public Tourist   getTourist()  { return tourist; }
    public TourGuide getGuide()    { return guide; }
    public int       getScore()    { return score; }
    public String    getFeedback() { return feedback; }

    @Override
    public String toString() {
        return "Rating[" + ratingID + "] " + tourist.getName()
               + " → " + guide.getName() + ": " + score + "/5 — " + feedback;
    }
}
