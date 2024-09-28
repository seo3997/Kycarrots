import FullCalendar from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

export default {
    template: '<div id="calendar"></div>',
    props: ['events'],
    mounted() {
        const calendarEl = this.$el;
        const calendar = new FullCalendar.Calendar(calendarEl, {
            plugins: [ dayGridPlugin, interactionPlugin ],
            initialView: 'dayGridMonth',
            events: this.events
        });
        calendar.render();
    }
};
