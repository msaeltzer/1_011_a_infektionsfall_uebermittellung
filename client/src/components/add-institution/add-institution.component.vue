<template src="./add-institution.component.html"></template>
<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import Api from '../../api/Api';

@Component({
  name: 'AddInstitutionComponent',
})

export default class AddInstitutionComponent extends Vue {
    
    public checked: boolean; 
    public dateFormat: string; 
    public form: any;
    constructor(){
        super();
        this.checked = false;
        this.dateFormat = "DD/MM/YYYY";
        this.form = this.$form.createForm(this, )
    }

    onCheck(e: any) {
      this.checked = e.target.checked;
    }


    handleSubmit(e: any) {

      e.preventDefault();

      this.form.validateFields((err: any, values: AnalyserNode) => {
        // TODO: Send request to BE if checked === true
        if (this.checked === true) {
          Api.postInstitution(values)
            .then((res) => {
              console.log(res);
              this.$message.info("Created your Institution!");
            })
            .catch(err => this.$message.info(err));
        }

        // JSON.stringify(values);
        if (!err) {
          console.log("Received values of form: ", values);
        }
      });
    }
}
</script>

<style lang="scss" src="./add-institution.component.scss"></style>
