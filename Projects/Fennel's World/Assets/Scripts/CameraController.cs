using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{
    public Transform target;
    public Vector3 offset;
    public float rotationSpeed;
    public bool invertCamera;
    public Transform pivot;
    public float maxView;
    public float minView;
    // Start is called before the first frame update
    void Start()
    {
        offset = target.position - transform.position;
        pivot.transform.position = target.transform.position;
        pivot.transform.parent = target.transform;
        Cursor.lockState = CursorLockMode.Locked;
    }

    // Update is called once per frame
    void LateUpdate()
    {
        float mouse_x = Input.GetAxis("Mouse X") * rotationSpeed;
        target.Rotate(0, mouse_x, 0);

        float mouse_y = Input.GetAxis("Mouse Y") * rotationSpeed;
        
        if(invertCamera){
            pivot.Rotate(mouse_y, 0, 0);
        }else{
            pivot.Rotate(-mouse_y, 0, 0);
        }

        if(pivot.rotation.eulerAngles.x > maxView && pivot.rotation.eulerAngles.x < 180f){
            pivot.rotation = Quaternion.Euler(maxView, 0, 0);
        }
       if(pivot.rotation.eulerAngles.x > 180f && pivot.rotation.eulerAngles.x < 360f + minView){
            pivot.rotation = Quaternion.Euler(360f + minView, 0, 0);
        }

        float y_angle = target.eulerAngles.y;
        float x_angle = pivot.eulerAngles.x;
        Quaternion rotation = Quaternion.Euler(x_angle, y_angle, 0);
        transform.position = target.position - (rotation * offset);
       
        if(transform.position.y < target.position.y){
            transform.position = new Vector3(transform.position.x, target.position.y, transform.position.z);
        }
        transform.LookAt(target);
    }
}
