package com.example.quangadmin.smsencrypfinal.Danhba;

import java.io.Serializable;

/**
 * Created by Nhocnhinho on 7/13/2015.
 */
public class Contact_IDs implements Serializable{


        private String names, phoneNumbers;
        public Contact_IDs(String names, String phoneNumbers) {
            super();
            this.names = names;
            this.phoneNumbers = phoneNumbers;
        }
        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public String getPhoneNumbers() {
            return phoneNumbers;
        }

        public void setPhoneNumbers(String phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }



}
