using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class EnemyController : MonoBehaviour
{
    public Transform playerTarget;
    public float aggroRange;
    public Transform[] destinations;
    private int currPoint;
    private NavMeshAgent navmesh;
    private bool isAggro;

    void Start()
    {
        navmesh = GetComponent<NavMeshAgent>();

    }
    void Update()
    {
        float distanceToPlayer = Vector3.Distance(transform.position, playerTarget.position);
        //Checks if player is within range of the enemy
        if (distanceToPlayer <= aggroRange)
        {
            isAggro = true;
            //Make the enemy look at the player
            transform.LookAt(playerTarget);
            Vector3 move = Vector3.MoveTowards(transform.position, playerTarget.position, 100f);
            //Move the enemy towards the player
            navmesh.destination = move;

        }
        else
        {
            //If player is out of range, enemy returns to patrolling
            isAggro = false;
            ReturnToPath();
        }
    }
    void ReturnToPath()
    {
        //Update the enemy's destination when close to current point
        if (!isAggro && navmesh.remainingDistance < 0.5f)
        {
            navmesh.destination = destinations[currPoint].position;
            UpdatePoint();
        }
    }
    void UpdatePoint()
    {
        //If enemy is at end of patrol, restart the patrol path
        if (currPoint == destinations.Length - 1)
        {
            currPoint = 0;
        }
        else
        {
            //Otherwise go to next patrol point
            currPoint++;
        }
    }

}
