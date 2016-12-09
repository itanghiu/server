/**
 * Created by i-tang on 07/09/16.
 */
define(function (require) {
    var Backbone = require('Backbone');
    var HskListModel = require('../models/HskListModel');
    var $ = require('jquery');

    var HsklistButtonView = Backbone.View.extend({

            initialize: function () {
                this.render();
            },

            render: function () {
                var html = '<br><input id="addList" type="button" value = "Add list"> ';
                $(this.el).html(html);
                return this;
            },

            // Handle HTML events.
            events: {
                'click #addList': 'addHskList'
            },

            addList: function () {

                var name = prompt('Enter list name', 'List1');
                var bookName = prompt('Enter bookName', 'Book1');
                var volumeNbr = prompt('Enter volumeNbr', '1');
                var hskListModel = new HskListModel({
                    name: name,
                    bookName: bookName,
                    volumeNbr: volumeNbr
                });
                this.collection.add(hskListModel);
                hskListModel.save({}, {
                    success: function (e) {
                        console.log('HskList saved to server id:' + hskListModel.get('id'));
                    },
                    error: function (e) {
                        console.log('Error saving HskList');
                        alert("Something went wrong while add hskList (" + model.get('id') + ').');
                    }
                });
            }
        })
        ;
    return HsklistButtonView;
})