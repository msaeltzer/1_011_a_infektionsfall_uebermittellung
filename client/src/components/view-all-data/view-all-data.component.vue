<template src="./view-all-data.component.html"></template>
<script lang="ts">

import { Component, Prop, Vue } from 'vue-property-decorator';
import { data_fill, columns_data } from './data';
@Component({
  name: 'PubicStatisticComponent',
})

export default class ViewAllDataComponent extends Vue {


    public entities: any [];
    public content: string;
    public columns: any;
    public data: any;

    constructor(){
        super();
        this.entities = [];
        this.content = "";
        this.columns = columns_data;
        this.data = data_fill;
    }

    create(e: any) {
      e.preventDefault();

      if (this.content.length > 0) {
        fetch("/exampleEntities", {
          method: "POST",
          body: JSON.stringify({ content: this.content }),
          headers: {
            "Content-Type": "application/json"
          }
        })
          .then(response => {
            return response.json();
          })
          .then(entity => {
            this.entities.push(entity);
          });
        this.content = "";
      }
    }

    created() {
        fetch("/exampleEntities")
        .then(response => {
            return response.json();
        })
        .then(entities => {
            this.entities.push(...entities);
        });
    }

}
</script>

<style lang="scss" src="./view-all-data.component.scss"></style>
