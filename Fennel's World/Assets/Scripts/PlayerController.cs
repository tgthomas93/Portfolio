using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour
{
    public float speed;
    public float jumpHeight;
    public float gravityFactor;
    public CharacterController player;
    public Vector3 direction;
    public Animator animator;
    public float fallThreshold;
    public float lives;
    public Text livesleft;
    public AudioSource sound;
    private bool hitByEnemy;

    void Start()
    {
        player = GetComponent<CharacterController>();
        animator = GetComponent<Animator>();
        livesleft.text = string.Format("Lives Left: {0}", lives);

    }

    void Update()
    {
        float temp_y = direction.y;
        direction = (transform.forward * Input.GetAxis("Vertical"));
        direction = direction.normalized * speed;
        direction.y = temp_y;

        if (player.isGrounded)
        {
            if (Input.GetButtonDown("Jump"))
            {
                direction.y = jumpHeight;
            }
        }

        direction.y = direction.y + (Physics.gravity.y * gravityFactor * Time.deltaTime);
        player.Move(direction * Time.deltaTime);
        animator.SetFloat("speed", (Mathf.Abs(Input.GetAxis("Vertical"))));

    }
    //Check if player has fallen off the map
    void FixedUpdate()
    {
        if (transform.position.y < fallThreshold)
        {
            Respawn();
        }
        if(hitByEnemy){
            Respawn();
        }
    } 

    //Checks for collisions with peaches
    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "peach")
        {
            FindObjectOfType<GameController>().collectFruit();
            Destroy(other.gameObject);
        }
        if(other.gameObject.tag == "enemy"){
            hitByEnemy = true;
        }
    }

    //Respawn player if they have died and check if game over
    private void Respawn()
    {
        hitByEnemy = false;
        lives--;
        sound.Play();
        livesleft.text = string.Format("Lives Left: {0}", lives);
        if (lives <= 0)
        {
            Cursor.lockState = CursorLockMode.None;
            SceneManager.LoadScene("GameOver");

        }
        else
        {
            transform.position = new Vector3(0, 0.2f, 0);
        }
    }
}
