package at.ac.tuwien.infosys.rosebery.storm.spout;

import at.ac.tuwien.infosys.rosebery.scenario.Factory;
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService;
import at.ac.tuwien.infosys.rosebery.scenario.Scenario;
import backtype.storm.spout.ISpout;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Bernhard Nickel, e0925384, e0925384@student.tuwien.ac.at
 */
public class RoseberySpout<T> extends BaseRichSpout implements ISpout {
    private SpoutOutputCollector collector;
    private Factory<T> factory;
    private Scenario<T> scenario;
    private String fieldName;
    private boolean scenarioRunning = false;

    private BlockingQueue<T> queue = new ArrayBlockingQueue<T>(1);

    private PopulationService<T> populationService = new PopulationService<T>() {
        @Override
        public void populate(T t) {
            try {
                queue.put(t);
            } catch (InterruptedException e) {}
        }
    };

    public RoseberySpout() {
    }

    public RoseberySpout(Factory<T> factory, Scenario<T> scenario, String fieldName) {
        this.factory = factory;
        this.scenario = scenario;
        this.fieldName = fieldName;
    }

    public void setFactory(Factory<T> factory) {
        this.factory = factory;
    }

    public void setScenario(Scenario<T> scenario) {
        this.scenario = scenario;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(fieldName));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        startScenario();
    }

    @Override
    public void nextTuple() {
        try {
            collector.emit(new Values(queue.take()));
        } catch (InterruptedException e) {}
    }

    private void startScenario() {
        if (scenario != null && !scenarioRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    scenario.run(factory, populationService);
                }
            }).start();

            scenarioRunning = true;
        }
    }
}
