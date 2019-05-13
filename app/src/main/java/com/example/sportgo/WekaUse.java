package com.example.sportgo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;



public class WekaUse {

	public static int getResult(String path, Instance inputNum) throws FileNotFoundException, Exception 
	{
		int result = 0;
		Instance dataRaw = inputNum;
		RandomForest rf = (RandomForest) SerializationHelper.read(new FileInputStream(path));
		
		double label = rf.classifyInstance(dataRaw);
        
        System.out.println(label);
        if(label <= 0.5) 
        {
        	result = 0;
        }
        else 
        {
        	result = 1;
        }
        System.out.println(result);
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int resultNum;
		String path = "Accle_model.model";
		
		
		/*
		 * input hint!
		 * 1. Announce a ArrayList<Attribute> object
		 * 2. Add your attribute
		 * 3. Announce a empty Instances
		 * 4. Set your attributes
		 * 5. Create a DenseInstance & add it into your empty Instances
		 * 6. Call function
		 * */
		//Announce ArrayList<Attribute>
		ArrayList<Attribute> adjust = new ArrayList<Attribute>();
		adjust.add(new Attribute("accr_x"));
		adjust.add(new Attribute("accr_y"));
		adjust.add(new Attribute("accr_z"));
		
		//Announce instances
		Instances data = new Instances("TestInstances", adjust, 0);
		data.setClassIndex(data.numAttributes() - 1);

		//Set attributes
		double testValue[] = {-10.27, -6.35, -0.225};
		
		//Create a DenseInstance
		Instance testInstance = new DenseInstance(1.0, testValue);
		testInstance.setDataset(data);
		
		//Call function
		resultNum = getResult(path, testInstance);
		

	}

}
