using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FruitController : MonoBehaviour {

	public GameObject fruit;
	void Start () 
	{
		
	}
    [System.Obsolete]
    void Update () 
	{
		fruit.transform.RotateAround(Vector3.up, Time.deltaTime);
	}
}
