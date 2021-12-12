/*


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package controllers

import (
	"context"
	"fmt"
	"github.com/go-logr/logr"
	corev1 "k8s.io/api/core/v1"
	netv1beta "k8s.io/api/networking/v1beta1"
	apierrors "k8s.io/apimachinery/pkg/api/errors"
	metav1 "k8s.io/apimachinery/pkg/apis/meta/v1"
	"k8s.io/apimachinery/pkg/runtime"
	"k8s.io/apimachinery/pkg/util/intstr"
	ctrl "sigs.k8s.io/controller-runtime"
	"sigs.k8s.io/controller-runtime/pkg/client"
	youqinsunv1 "youqin.sun/api/v1"
)

// DummySiteReconciler reconciles a DummySite object
type DummySiteReconciler struct {
	client.Client
	Log    logr.Logger
	Scheme *runtime.Scheme
}

// +kubebuilder:rbac:groups=youqin.sun.youqin.sun,resources=dummysites,verbs=get;list;watch;create;update;patch;delete
// +kubebuilder:rbac:groups=youqin.sun.youqin.sun,resources=dummysites/status,verbs=get;update;patch
// +kubebuilder:rbac:groups=core,resources=pods,verbs=get;list;watch;create;update;patch;delete
// +kubebuilder:rbac:groups=core,resources=pods/status,verbs=get
// +kubebuilder:rbac:groups=core,resources=services,verbs=get;list;watch;create;update;patch;delete
// +kubebuilder:rbac:groups=core,resources=services/status,verbs=get
// +kubebuilder:rbac:groups=extensions,resources=ingresses,verbs=get;list;watch;create;update;patch;delete
// +kubebuilder:rbac:groups=extensions,resources=ingresses/status,verbs=get
// +kubebuilder:rbac:groups=networking.k8s.io,resources=ingresses,verbs=get;list;watch;create;update;patch;delete
// +kubebuilder:rbac:groups=networking.k8s.io,resources=ingresses/status,verbs=get

// +kubebuilder:rbac:groups=webapp.my.domain,namespace=default,resources=guestbooks/status,verbs=get;update;patch
func (r *DummySiteReconciler) Reconcile(req ctrl.Request) (ctrl.Result, error) {
	ctx := context.Background()
	_ = r.Log.WithValues("dummysite", req.NamespacedName)

	var dummySite youqinsunv1.DummySite
	if err := r.Get(ctx, req.NamespacedName, &dummySite); err != nil {
		fmt.Println(err, "unable to fetch DummySite")
		// we'll ignore not-found errors, since they can't be fixed by an immediate
		// requeue (we'll need to wait for a new notification), and we can get them
		// on deleted requests.
		return ctrl.Result{}, client.IgnoreNotFound(err)
	}

	// Is there a pod created for that DummySite
	var pod corev1.Pod
	podFound := true
	if err := r.Get(ctx, req.NamespacedName, &pod); err != nil {
		if !apierrors.IsNotFound(err) {
			fmt.Println(err, "unable to check existence of POD")
			return ctrl.Result{}, err
		}
		podFound = false
	}

	if podFound {
		fmt.Println("POD exists")
		return ctrl.Result{}, nil
	}

	pod_pointer, err := createPodForDummySite(&dummySite, r)

	if err != nil {
		fmt.Println(err, "unable to construct pod template")
		return ctrl.Result{}, err
	}

	// ...and create it on the cluster
	if err := r.Create(ctx, pod_pointer); err != nil {
		fmt.Println(err, "unable to create Pod  on the cluster for DummySite")
		return ctrl.Result{}, err
	}

	fmt.Println("created Pod for DummySite", pod.Name)
	fmt.Println("created Pod for DummySite")

	//
	// Is there a service created for that DummySite
	var svc corev1.Service
	svcFound := true
	if err := r.Get(ctx, req.NamespacedName, &svc); err != nil {
		if !apierrors.IsNotFound(err) {
			fmt.Println(err, "unable to check existence of Service")
			return ctrl.Result{}, err
		}
		svcFound = false
	}

	if svcFound {
		fmt.Println("Service exists")
		return ctrl.Result{}, nil
	}

	svc_pointer, err := createServiceForDummySite(&dummySite, r)

	if err != nil {
		fmt.Println(err, "unable to construct service template")
		return ctrl.Result{}, err
	}

	// ...and create it on the cluster
	if err := r.Create(ctx, svc_pointer); err != nil {
		fmt.Println(err, "unable to create Service  on the cluster for DummySite")
		return ctrl.Result{}, err
	}

	fmt.Println("created Service for DummySite")

	//
	// Is there an ingress
	var ingress netv1beta.Ingress
	ingressFound := true
	if err := r.Get(ctx, req.NamespacedName, &ingress); err != nil {
		if !apierrors.IsNotFound(err) {
			fmt.Println(err, "unable to check existence of Ingress")
			return ctrl.Result{}, err
		}
		ingressFound = false
	}

	if ingressFound {
		fmt.Println("Ingress exists")
		return ctrl.Result{}, nil
	}

	ingress_pointer, err := createIngressForDummySite(&dummySite, r)

	if err != nil {
		fmt.Println(err, "unable to construct ingress template")
		return ctrl.Result{}, err
	}

	// ...and create it on the cluster
	if err := r.Create(ctx, ingress_pointer); err != nil {
		fmt.Println(err, "unable to create Ingress  on the cluster for DummySite")
		return ctrl.Result{}, err
	}

	fmt.Println("created Ingress for DummySite")

	return ctrl.Result{}, nil
}
func createPodForDummySite(dummySite *youqinsunv1.DummySite, r *DummySiteReconciler) (*corev1.Pod, error) {
	name := fmt.Sprintf(dummySite.Name)
	pod := &corev1.Pod{
		ObjectMeta: metav1.ObjectMeta{
			Labels: map[string]string{
				"app": name,
			},
			Name:      name,
			Namespace: dummySite.Namespace,
		},
		Spec: corev1.PodSpec{
			Containers: []corev1.Container{
				{
					Name:            name,
					Image:           "lnsth/dummysite:exercise_5.01",
					ImagePullPolicy: "Always",
					Ports: []corev1.ContainerPort{
						{
							Name:          "http",
							Protocol:      corev1.ProtocolTCP,
							ContainerPort: 3000,
						},
					},
					Env: []corev1.EnvVar{
						{
							Name:  "WEBSITE_URL",
							Value: dummySite.Spec.Website_url,
						},
					},
				},
			},
		},
	}

	if err := ctrl.SetControllerReference(dummySite, pod, r.Scheme); err != nil {
		return nil, err
	}

	return pod, nil
}

func createServiceForDummySite(dummySite *youqinsunv1.DummySite, r *DummySiteReconciler) (*corev1.Service, error) {
	name := fmt.Sprintf(dummySite.Name)

	svc := &corev1.Service{
		ObjectMeta: metav1.ObjectMeta{
			Name:      name,
			Namespace: dummySite.Namespace,
		},
		Spec: corev1.ServiceSpec{
			Ports: []corev1.ServicePort{
				{Name: "port1", Protocol: corev1.ProtocolTCP, Port: 80, TargetPort: intstr.IntOrString{Type: intstr.Int, IntVal: 3000}},
			},
			Selector: map[string]string{
				"app": name,
			},
			Type: corev1.ServiceTypeClusterIP,
		},
	}

	if err := ctrl.SetControllerReference(dummySite, svc, r.Scheme); err != nil {
		return nil, err
	}

	return svc, nil
}

func createIngressForDummySite(dummySite *youqinsunv1.DummySite, r *DummySiteReconciler) (*netv1beta.Ingress, error) {
	name := fmt.Sprintf(dummySite.Name)

	ingress := &netv1beta.Ingress{
		ObjectMeta: metav1.ObjectMeta{
			Name:      name,
			Namespace: dummySite.Namespace,
		},
		Spec: netv1beta.IngressSpec{
			Rules: []netv1beta.IngressRule{
				{
					IngressRuleValue: netv1beta.IngressRuleValue{
						HTTP: &netv1beta.HTTPIngressRuleValue{
							Paths: []netv1beta.HTTPIngressPath{
								{
									Path: "/",
									Backend: netv1beta.IngressBackend{
										ServiceName: name,
										ServicePort: intstr.IntOrString{Type: intstr.Int, IntVal: 80},
									},
								},
							},
						},
					},
				},
			},
		},
	}

	if err := ctrl.SetControllerReference(dummySite, ingress, r.Scheme); err != nil {
		return nil, err
	}

	return ingress, nil
}

func (r *DummySiteReconciler) SetupWithManager(mgr ctrl.Manager) error {
	return ctrl.NewControllerManagedBy(mgr).
		For(&youqinsunv1.DummySite{}).
		Complete(r)
}
