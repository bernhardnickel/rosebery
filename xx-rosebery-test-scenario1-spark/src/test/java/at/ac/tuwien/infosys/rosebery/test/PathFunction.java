package at.ac.tuwien.infosys.rosebery.test;

import at.ac.tuwien.infosys.rosebery.test.model.Node;
import at.ac.tuwien.infosys.rosebery.test.model.NodeList;
import at.ac.tuwien.infosys.rosebery.test.model.PathObject;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.nio.file.Paths;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class PathFunction implements FlatMapFunction<NodeList, PathObject> {
    private static final int MAX_PATHS = 200000;

    @Override
    public Iterable<PathObject> call(NodeList nodeList) throws Exception {
        List<PathObject> result = new ArrayList<>();
        List<List<Integer>> paths = new ArrayList<>();

        permute(nodeList.getNodes(), new ArrayList<>(), 0, paths);

        String id = UUID.randomUUID().toString();

        int i = 0;

        for (; (i + MAX_PATHS) < paths.size(); i += MAX_PATHS) {
            result.add(new PathObject(id, nodeList.getNodes(), paths.subList(i, i + MAX_PATHS)));
        }

        result.add(new PathObject(id, nodeList.getNodes(), paths.subList(i, paths.size())));


        return result;
    }
    private void permute(List<Node> lst, List<Integer> h, int i, List<List<Integer>> r) {
        if (i == lst.size() - 1) {
            for (int j = 0; j < lst.size(); j++) {
                if (!h.contains(lst.get(j).getId())) {
                    List<Integer> nh = new ArrayList<>(h);
                    nh.add(lst.get(j).getId());
                    r.add(nh);
                }
            }
            return;
        }

        for (int j = 0; j < lst.size(); j++) {
            if (!h.contains(lst.get(j).getId())) {
                List<Integer> nh = new ArrayList<>(h);
                nh.add(lst.get(j).getId());
                permute(lst, nh, i + 1, r);
            }
        }
    }
}
