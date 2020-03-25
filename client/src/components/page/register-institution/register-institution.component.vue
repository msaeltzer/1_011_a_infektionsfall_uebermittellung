<template src="./register-institution.component.html"></template>
<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import { randomizeProperties } from '../../../util/randomize';
import Api from '../../../api/Api';

@Component({
  name: 'RegisterInstitutionComponent',
})

export default class RegisterInstitutionComponent extends Vue {

  public checked: boolean; 
  public form: any;
  public createdInstitution: any;
  public dataProcessingClass: any; 

  constructor(){
      super();
      this.checked = false;
      this.createdInstitution = null;
      this.dataProcessingClass = "";
  }

  handleSubmit(e: any) {
        e.preventDefault();

        this.form.validateFields((err: any, values: any) => {
          if (!this.checked) {
            this.dataProcessingClass = "data-processing-not-selected";
            return;
          } else if (err) {
            return;
          }

          // TODO: Remove this when we go to production
          randomizeProperties(
            [
              "name",
              "email",
              "phoneNumber",
              "street",
              "houseNumber",
              { key: "zip", type: "number" },
              "city"
            ],
            values
          );

          if (this.checked === true) {
            Api.postInstitution(values)
              .then(institution => {
                this.createdInstitution = institution;

                const notification = {
                  message: "Institution registriert.",
                  description: "Die Institution wurde erfolgreich registriert."
                };
                this.$notification["success"](notification);
              })
              .catch(err => {
                const notification = {
                  message: "Fehler beim Registrieren der Institution.",
                  description: err.message
                };
                this.$notification["error"](notification);
              });
          }
        });
      }
}
</script>

<style lang="scss" src="./register-institution.component.scss"></style>
