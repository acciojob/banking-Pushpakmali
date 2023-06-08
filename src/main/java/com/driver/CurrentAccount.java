package com.driver;

import java.util.Collection;
import java.util.HashMap;

public class CurrentAccount extends BankAccount{
    String tradeLicenseId; //consists of Uppercase English characters only
    private static double minBalance = 5000;

    public CurrentAccount(String name, double balance, String tradeLicenseId) throws Exception {
        // minimum balance is 5000 by default. If balance is less than 5000, throw "Insufficient Balance" exception
        super(name, balance, this.minBalance);

        if(balance < 5000){
            throw new Exception("Insufficient Balance");
        }

        this.tradeLicenseId = tradeLicenseId;
        validateLicenseId();
    }

    public void validateLicenseId() throws Exception {
        // A trade license Id is said to be valid if no two consecutive characters are same
        // If the license Id is valid, do nothing
        // If the characters of the license Id can be rearranged to create any valid license Id
        // If it is not possible, throw "Valid License can not be generated" Exception

        boolean isValid = true;
        int n = this.tradeLicenseId.length();

        for(int i=0;i<n-1;i++){
            if(this.tradeLicenseId.charAt(i) == this.tradeLicenseId.charAt(i+1)){
                isValid = false;
                break;
            }
        }

        if(isValid == false)return;

        HashMap<Character, Integer> hm = new HashMap<>();
        for(int i=0;i<n;i++){
            hm.put(this.tradeLicenseId.charAt(i), hm.getOrDefault(this.tradeLicenseId.charAt(i), 0)+1);
        }

        int mapSize = hm.size();

        for(Character key : hm.keySet()){
            if(mapSize%2 == 1 && hm.get(key) > (mapSize/2)+1){
                throw new Exception("Valid License can not be generated");
            }
            else if(mapSize%2==0 && hm.get(key) > (mapSize/2)){
                throw new Exception("Valid License can not be generated");
            }
        }

        char[] copyLicence = new char[n];
        boolean reachedEnd = false;
        int idx = 0;

        for(char ch : hm.keySet()){
            int k = hm.get(ch);
            int i=0;

            if(!reachedEnd){
                for(;i<k;i++){
                    copyLicence[idx] = ch;
                    idx += 2;

                    if(idx >= n){
                        reachedEnd=true;
                        idx=1;
                        break;
                    }
                }
            }

            if(reachedEnd){
                for(;i<k;i++){
                    copyLicence[idx] = ch;
                    idx += 2;
                    if(idx >= n){
                        break;
                    }
                }
            }
        }

        this.tradeLicenseId = new String(copyLicence);
    }

}
