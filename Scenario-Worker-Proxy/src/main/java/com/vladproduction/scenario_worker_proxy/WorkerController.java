package com.vladproduction.scenario_worker_proxy;


public class WorkerController{

    //algorithm#1: worker WORK ALL TIME, and no matter if any scenario or proxy exist in queues

    //algorithm#2: worker START TO WORK ONLY, if at least one of (scenario or proxy) exist

    //algorithm#3: worker START TO WORK ONLY, if at least by one of each scenario and proxy exist

    //algorithm#4: worker START TO WORK ONLY, in depend on min and max of quantity in queues

    //algorithm#5: worker WORK ALL TIME, but some ALARM work to do in PRIORITY

    //could be more cases...


}
