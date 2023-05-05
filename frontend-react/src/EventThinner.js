export class EventThinner {

    delta;
    shortDelta;

    futureUpdate = 0;
    latestUpdate = 1;

    constructor(delta, shortDelta) {
        this.delta = delta;
        this.shortDelta = shortDelta;        
    }

    run(callback) {
        let prevUpdate = this.latestUpdate;
        this.latestUpdate = Date.now();
        if (this.futureUpdate > this.latestUpdate) {
            // update scheduled for future, do nothing until that update fires
            return;
        }
        let requestedDelay = 0;
        if ((this.latestUpdate - prevUpdate) > this.delta) {
            // schedule for now, plus a little bit in case they're still typing
            requestedDelay = this.shortDelta;
        } else {
            // schedule for later
            requestedDelay = this.delta;
        }
        this.futureUpdate = requestedDelay + this.latestUpdate;
        setTimeout(callback, requestedDelay);        
    }
    
};


