import at.ac.tuwien.infosys.rosebery.scenario.Factory
import at.ac.tuwien.infosys.rosebery.scenario.PopulationService
import at.ac.tuwien.infosys.rosebery.scenario.Scenario
import at.ac.tuwien.infosys.rosebery.scenario.provided.*


//Template groovy script
def loop(count) {
    new LoopScenario(count)
}

def loop(int count, long sleepTime) {
    new LoopScenario(count, sleepTime)
}

def loop(Scenario scenario, int count) {
    new LoopScenario(scenario, count)
}

def loop(scenario, count, sleepTime) {
    new LoopScenario(scenario, count, sleepTime)
}

def random(Scenario... scenarios) {
    new RandomScenario(scenarios)
}

def once() {
    new RunOnceScenario()
}

def list(Scenario... scenarios) {
    new ListScenario(scenarios)
}

