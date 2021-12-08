using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LevelManager : MonoBehaviour
{
    public void Restart(){
        SceneManager.LoadScene("Fennel's_World");
    }
    public void gameEnd(){
        Application.Quit();
    }
}
