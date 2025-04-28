import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationEngine {
    public static void main(String[] args) {
        try {
            // Load your data
            DataModel model = new FileDataModel(new File("data.csv"));

            // Calculate similarity between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find the nearest 2 users
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build a recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend 3 items for user with ID 2
            List<RecommendedItem> recommendations = recommender.recommend(2, 3);

            // Show recommendations
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended Item: " + recommendation.getItemID() + " with value: " + recommendation.getValue());
            }
        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}
