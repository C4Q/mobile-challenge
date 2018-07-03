package nyc.c4q.vice.mobile.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    @SerializedName("results")
    private List<Results> results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("page")
    private int page;

    public List<Results> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getPage() {
        return page;
    }

    public static class Results {
        @SerializedName("release_date")
        private String release_date;
        @SerializedName("overview")
        private String overview;
        @SerializedName("adult")
        private boolean adult;
        @SerializedName("backdrop_path")
        private String backdrop_path;
        @SerializedName("genre_ids")
        private List<Integer> genre_ids;
        @SerializedName("original_title")
        private String original_title;
        @SerializedName("original_language")
        private String original_language;
        @SerializedName("poster_path")
        private String poster_path;
        @SerializedName("popularity")
        private double popularity;
        @SerializedName("title")
        private String title;
        @SerializedName("vote_average")
        private double vote_average;
        @SerializedName("video")
        private boolean video;
        @SerializedName("id")
        private int id;
        @SerializedName("vote_count")
        private int vote_count;

        public String getRelease_date() {
            return release_date;
        }

        public String getOverview() {
            return overview;
        }

        public boolean getAdult() {
            return adult;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getTitle() {
            return title;
        }

        public double getVote_average() {
            return vote_average;
        }

        public boolean getVideo() {
            return video;
        }

        public int getId() {
            return id;
        }

        public int getVote_count() {
            return vote_count;
        }
    }
}
