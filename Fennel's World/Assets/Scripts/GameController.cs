using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class GameController : MonoBehaviour
{
    private int fruitLeft;
    private GameObject[] totalFruit;
    public Text fruitText;
    public Text winText;
    public AudioSource fruitSound;
    void Start()
    {

        totalFruit = GameObject.FindGameObjectsWithTag ("peach");
        fruitLeft = totalFruit.Length;
        fruitText.text = string.Format("Peaches Left: {0}", fruitLeft);
        
        
    }

    void Update()
    {
        if(Input.GetButtonDown("Cancel")){
            if(Cursor.lockState == CursorLockMode.None){
                Cursor.lockState = CursorLockMode.Locked;
            }else{
                Cursor.lockState = CursorLockMode.None;
            }
            
        }
    }

    public void collectFruit(){
        fruitLeft--;
        fruitSound.Play();
        fruitText.text = string.Format("Peaches Left: {0}", fruitLeft);
        if(fruitLeft == 0){
            Cursor.lockState = CursorLockMode.None;
            SceneManager.LoadScene("Gamewin");
        }
    }
}
