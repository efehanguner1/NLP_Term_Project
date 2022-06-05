package org.example;

import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSentenceNormalizer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Path lookupRoot = Paths.get("C:\\Users\\Efehan\\Desktop\\Zemberek\\normalization");
        Path lmFile = Paths.get("C:\\Users\\Efehan\\Desktop\\Zemberek\\lm.2gram.slm");
        TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
        TurkishSentenceNormalizer normalizer = new TurkishSentenceNormalizer(morphology, lookupRoot, lmFile);
        System.out.println(normalizer.normalize("ve kim şeröristlerll konuşur ki ?"));


        List<String> noisy_test = new ArrayList<String>();
        List<String> normal_test = new ArrayList<String>();
        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("C:\\Users\\Efehan\\Desktop\\Turkish_Noise_Data\\tr_noisy.test");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console

                //System.out.println (strLine);
                noisy_test.add(strLine);
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        try{
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream("C:\\Users\\Efehan\\Desktop\\Turkish_Noise_Data\\tr.test");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console

                //System.out.println (strLine);
                normal_test.add(strLine);
            }
            //Close the input stream
            in.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        float Recall_sum=0,Precision_sum=0,F1_sum =0;
        for(int i = 0; i < normal_test.size(); i++) {
            //System.out.println("Current index is: " + i);
            float tp = 0,fp = 0 ,tn = 0, fn = 0;
            String normal_sent = normal_test.get(i);
            String noisy_sent = noisy_test.get(i);
            String decoded_sent = normalizer.normalize(noisy_sent);

            System.err.println("Input sentence: "+ noisy_sent);
            System.err.println("Decoded sentence: "+ decoded_sent);
            System.err.println("Original sentence: "+ normal_sent);

            for (int j = 0; j < normal_sent.length(); j++) {


                if (normal_sent.charAt(j) == decoded_sent.charAt(j) && noisy_sent.charAt(j) != normal_sent.charAt(j)) {
                    tp += 1;
                } else if (normal_sent.charAt(j) != decoded_sent.charAt(j) && noisy_sent.charAt(j) == normal_sent.charAt(j)) {
                    fp += 1;
                } else if (normal_sent.charAt(j) != decoded_sent.charAt(j) && noisy_sent.charAt(j) != normal_sent.charAt(j)) {
                    fn += 1;
                }
                if(j+1 == decoded_sent.length())
                {
                    break;
                }
            }
            float Recall,Precision,F1;
            if (tp == 0 && (fp==0 || fn==0)) {
                Recall = 0;
                Precision = 0;
                F1= 0;
            }
            else{
                Recall = tp/(tp+fn);
                Precision = tp/(tp+fp);
                F1 = 2*((Precision*Recall)/(Precision+Recall));
            }


            System.err.println("Recall is: "+ Recall);
            System.err.println("Precision is: "+ Precision);
            System.err.println("F1 Score is: "+ F1);
            Recall_sum += Recall;
            Precision_sum += Precision;
            if(F1 >= 0)
            {
                F1_sum += F1;
            }

        }
        System.err.println("Average Recall is: "+ Recall_sum/normal_test.size());
        System.err.println("Average Precision is: "+ Precision_sum/normal_test.size());
        System.err.println("Average F1 Score is: "+ F1_sum/normal_test.size());
    }
}
