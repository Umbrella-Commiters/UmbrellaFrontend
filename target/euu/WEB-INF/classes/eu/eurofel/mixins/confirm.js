var Confirm = Class.create();
Confirm.prototype = {
		
        initialize: function(elementId, message) {
            this.message = message;
            Event.observe($(elementId), 'click', this.doConfirm.bindAsEventListener(this));
        },
        
        doConfirm: function(e) {
            if (! confirm(this.message)) {
                    e.stop();
            }
        }
        
}